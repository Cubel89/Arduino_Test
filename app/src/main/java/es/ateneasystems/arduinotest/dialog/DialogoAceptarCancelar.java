package es.ateneasystems.arduinotest.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import es.ateneasystems.arduinotest.R;

/**
 * Created by cubel on 28/12/15.
 */
public class DialogoAceptarCancelar extends DialogFragment {

    private String titulo;
    private String mensaje;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

   /* public static DialogoAceptarCancelar newInstance(int title) {
        DialogoAceptarCancelar frag = new DialogoAceptarCancelar();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setIcon(R.mipmap.ic_launcher)
                // Set Dialog Title
                .setTitle(this.titulo)
                // Set Dialog Message
                .setMessage(this.mensaje)

                // Positive button
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        Log.d("Dialogo", "Aceptar");
                    }
                })

                // Negative Button
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        Log.d("Dialogo", "Cancelar");
                    }
                }).create();
    }


}
