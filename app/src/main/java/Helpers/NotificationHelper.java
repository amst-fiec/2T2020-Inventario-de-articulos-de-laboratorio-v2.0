package Helpers;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.labstock.BuildConfig;

public class NotificationHelper extends Application {
    public static final String CANAL_1_ID="canal1";
    private static final String CANAL_1_NOMBRE="canal1";
    private static final String CANAL_1_DESCRIPCION="canal1";



    @Override
    public void onCreate(){
        super.onCreate();
        crearCanalesNotificaciones();
    }

    public void crearCanalesNotificaciones(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel canal1= new NotificationChannel(
              CANAL_1_ID,
                    CANAL_1_NOMBRE,
                    NotificationManager.IMPORTANCE_HIGH
            );
            canal1.setDescription(CANAL_1_DESCRIPCION);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal1);
        }
    }
}
