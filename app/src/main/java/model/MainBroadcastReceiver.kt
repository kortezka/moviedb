package model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
                Toast.makeText(context, "работа интрнета переключена", Toast.LENGTH_LONG).show()
            }
        }
