package is.hello.piru;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import is.hello.piru.api.ApiModule;
import is.hello.piru.bluetooth.BluetoothModule;
import is.hello.piru.ui.MainActivity;
import is.hello.piru.ui.dialogs.DownloadDialogFragment;
import is.hello.piru.ui.screens.DfuCompleteFragment;
import is.hello.piru.ui.screens.DfuProgressFragment;
import is.hello.piru.ui.screens.SelectFirmwareFragment;
import is.hello.piru.ui.screens.SelectPillFragment;
import is.hello.piru.ui.screens.SignInFragment;

@Module(includes = {ApiModule.class, BluetoothModule.class},
        injects = {
                MainActivity.class,
                SignInFragment.class,
                SelectFirmwareFragment.class,
                DownloadDialogFragment.class,
                SelectPillFragment.class,
                DfuProgressFragment.class,
                DfuCompleteFragment.class,
        })
public class PiruAppModule {
    private final Context applicationContext;

    public PiruAppModule(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides Context provideApplicationContext() {
        return applicationContext;
    }
}
