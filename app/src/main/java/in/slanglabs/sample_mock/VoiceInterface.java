/** Note that this package name needs to be changed to suit your application ***/
package in.slanglabs.sample_mock;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import in.slanglabs.platform.SlangBuddy;
import in.slanglabs.platform.SlangBuddyOptions;
import in.slanglabs.platform.SlangIntent;
import in.slanglabs.platform.SlangLocale;
import in.slanglabs.platform.SlangSession;
import in.slanglabs.platform.action.SlangIntentAction;

/**
 * This is where all Slang specific handling goes
 */

public class VoiceInterface {
    private static String app_id = "your_buddy_id";
    private static String api_key = "your_buddy_key";
    private static Context appContext;

    /****************************************************************
     * App specific logic goes here
     ***************************************************************/
    private static class MyActionHandler implements SlangIntentAction {
        @Override
        public Status action(SlangIntent slangIntent, SlangSession slangSession) {
            // TODO: Insert the handler for the intents here
            Toast
                .makeText(
                    appContext,
                    "Intent detected - " + slangIntent.getName()),
                    Toast.LENGTH_LONG)
                .show(); 
            return Status.SUCESSS;
        }
    }

    /****************************************************************
     * Standard boiler plate goes here.
     ***************************************************************/
    // To initialize Slang in your application, simply call
    // VoiceInterface.init(context)
    public static void init(Context context) {
        appContext = context;

        try {
            SlangBuddyOptions options = new SlangBuddyOptions.Builder().setContext(context).setBuddyId(app_id)
                    .setAPIKey(api_key).setListener(new BuddyListener(context)).setIntentAction(new MyActionHandler())
                    .setRequestedLocales(SlangLocale.getSupportedLocales())
                    .setDefaultLocale(SlangLocale.LOCALE_ENGLISH_IN)
                    // change env to production when the buddy is published to production
                    .setEnvironment(SlangBuddy.Environment.STAGING).build();
            SlangBuddy.initialize(options);
        } catch (SlangBuddyOptions.InvalidOptionException e) {
            e.printStackTrace();
        } catch (SlangBuddy.InsufficientPrivilegeException e) {
            e.printStackTrace();
        }
    }

    private static class BuddyListener implements SlangBuddy.Listener {
        private Context appContext;

        public BuddyListener(Context appContext) {
            this.appContext = appContext;
        }

        @Override
        public void onInitialized() {
            Log.d("BuddyListener", "Slang Initialised Successfully");

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext, "Slang Initialised", Toast.LENGTH_LONG).show();
                }
            }, 10);
        }

        @Override
        public void onInitializationFailed(final SlangBuddy.InitializationError e) {
            Log.d("BuddyListener", "Slang failed:" + e.getMessage());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext, "Failed to initialise Slang:" + e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }, 10);
        }

        @Override
        public void onLocaleChanged(final Locale newLocale) {
            Log.d("BuddyListener", "Locale Changed:" + newLocale.getDisplayName());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext, "Locale Changed:" + newLocale.getDisplayName(), Toast.LENGTH_LONG)
                            .show();
                }
            }, 10);
        }

        @Override
        public void onLocaleChangeFailed(final Locale newLocale, final SlangBuddy.LocaleChangeError e) {
            Log.d("BuddyListener", "Locale(" + newLocale.getDisplayName() + ") Change Failed:" + e.getMessage());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(appContext,
                            "Locale(" + newLocale.getDisplayName() + ") Change Failed:" + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }, 10);
        }
    }
}
