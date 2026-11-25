package app.morphe.library.installation.installer

@Suppress("MemberVisibilityCanBePrivate")
internal object Constants {
    const val PLACEHOLDER = "PLACEHOLDER"

    const val TMP_FILE_PATH = "/data/local/tmp/morphe.tmp"
    const val MOUNT_PATH = "/data/adb/morphe/"
    const val MOUNTED_APK_PATH = "$MOUNT_PATH$PLACEHOLDER.apk"
    const val MOUNT_SCRIPT_PATH = "/data/adb/service.d/mount_morphe_$PLACEHOLDER.sh"

    const val EXISTS = "[[ -f $PLACEHOLDER ]] || exit 1"
    const val MOUNT_GREP = "grep $PLACEHOLDER /proc/mounts"
    const val DELETE = "rm -rf $PLACEHOLDER"
    const val CREATE_DIR = "mkdir -p"
    const val RESTART = "am start -S $PLACEHOLDER"
    const val KILL = "am force-stop $PLACEHOLDER"
    const val INSTALLED_APK_PATH = "pm path $PLACEHOLDER"

    // Code compiled for the APK that was under the stock path before no longer matches it, so ART would run
    // the app uncompiled until the device next compiles apps while idle. This compiles it as a fresh install
    // would, with the baseline profile ART reads from the APK on Android 15 and up.
    const val RECOMPILE = "pm compile --reset $PLACEHOLDER"
    const val CREATE_INSTALLATION_PATH = "$CREATE_DIR $MOUNT_PATH"

    const val MOUNT_APK =
        "base_path=\"$MOUNTED_APK_PATH\" && " +
            $$"mv $$TMP_FILE_PATH $base_path && " +
            $$"chmod 644 $base_path && " +
            $$"chown system:system $base_path && " +
            $$"chcon u:object_r:apk_data_file:s0  $base_path"

    // Run inside the single quotes of su -c, so it quotes with double quotes only
    const val UMOUNT =
        "grep $PLACEHOLDER /proc/mounts | " +
            $$"while read -r line; do echo $line | " +
            "cut -d \" \" -f 2 | " +
            "sed \"s/apk.*/apk/\" | " +
            "xargs -r umount -l; done"

    const val INSTALL_MOUNT_SCRIPT = "mv $TMP_FILE_PATH $MOUNT_SCRIPT_PATH && chmod +x $MOUNT_SCRIPT_PATH"

    // A script run on the device, where the shared storage is mounted at /sdcard
    @Suppress("SdCardPath")
    val MOUNT_SCRIPT =
        $$"""
        #!/system/bin/sh
        until [ "$( getprop sys.boot_completed )" = 1 ]; do sleep 3; done
        until [ -d "/sdcard/Android" ]; do sleep 1; done

        stock_path=$( pm path $$PLACEHOLDER | grep base | sed 's/package://g' )

        # Make sure the app is installed.
        if [ -z "$stock_path" ]; then
            exit 1
        fi

        # Unmount any existing installations to prevent multiple unnecessary mounts.
        $$UMOUNT

        base_path="$$MOUNTED_APK_PATH"

        chcon u:object_r:apk_data_file:s0 $base_path

        # Use Magisk mirror, if possible. Newer Magisk versions keep no data mirror.
        if command -v magisk &> /dev/null; then
            MIRROR="$(magisk --path)/.magisk/mirror"
            [ -f "$MIRROR$base_path" ] || MIRROR=""
        fi

        mount -o bind $MIRROR$base_path $stock_path

        # Kill the app to force it to restart the mounted APK in case it's currently running.
        $$KILL
        """.trimIndent()

    /**
     * Replaces the [PLACEHOLDER] with the given [replacement].
     *
     * @param replacement The replacement to use.
     * @return The replaced string.
     */
    operator fun String.invoke(replacement: String) = replace(PLACEHOLDER, replacement)
}
