package com.unilab.gmp.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.unilab.gmp.R;
import com.unilab.gmp.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by c_rcmiguel on 9/29/2017.
 */

public class NotificationCreator {

Context context;


    public NotificationCreator(Context context) {
this.context = context;
    }
    public void createNotification(int changes)
    {
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.drawable.logo_dart_header);
        mBuilder.setContentTitle("DART Notification");
        mBuilder.setContentText(changes + " template/s has been updated.");

                Intent resultIntent = new Intent(context, MainActivity.class);
                // Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
                PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
//                mBuilder.build();
        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        mBuilder.setContentIntent(resultPendingIntent);

        mBuilder.setAutoCancel(true);
//                mBuilder.flags |= Notification.FLAG_AUTO_CANCEL;

        mBuilder.setAutoCancel(true);

// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId,mBuilder.build());

//                Intent resultIntent = new Intent(this, Main.class);
//                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//                stackBuilder.addParentStack(ResultActivity.class);
//
//                // Adds the Intent that starts the Activity to the top of the stack
//                stackBuilder.addNextIntent(resultIntent);
//                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//                mBuilder.setContentIntent(resultPendingIntent);
    }
}
