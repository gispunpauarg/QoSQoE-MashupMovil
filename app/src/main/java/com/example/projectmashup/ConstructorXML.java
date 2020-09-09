package com.example.projectmashup;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Construye un documento XML a partir de las métricas que va recibiendo
 * mediante el método «adjuntarMetrica».
 * Esta clase fue desarrollada en base al trabajo de Juan Enriquez.
 * <p>
 * NOTA PARA EL DESARROLLADOR: El archivo XML en el que se almacenan las
 * métricas se guarda en el directorio raíz de la memoria del teléfono.
 * Lo puede ubicar con el nombre «metricas.xml».
 *
 * @author Ariel Machini
 */
public class ConstructorXML {

    /**
     * El nombre del archivo XML que se va a guardar (/‹NOMBRE AQUÍ›.xml).
     * El valor por defecto es «metricas».
     */
    public static String NOMBRE_ARCHIVO = "metricas";

    private static SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US);

    /**
     * Adjunta la métrica especificada por parámetros al archivo XML que
     * almacena las métricas.
     *
     * @param metrica El nombre de la métrica que se va a adjuntar. Por
     *                ejemplo, "latenciaMilisegundos".
     * @param valor   El valor correspondiente a la métrica especificada mediante
     *                la variable «metrica». Siguiendo con el ejemplo que se usó
     *                para el parámetro anterior, un ejemplo cualquiera de un
     *                valor apropiado sería «72» (milisegundos).
     * @author Ariel Machini
     */
    public static void adjuntarMetrica(String metrica, String valor) {
        File archivoXML = new File(Environment.getExternalStorageDirectory() + "/" + NOMBRE_ARCHIVO + ".xml");

        try {
            if (!archivoXML.exists()) {
                archivoXML.createNewFile();
            }

            Log.d("APIREST-path", archivoXML.getAbsolutePath());
            Log.d("APIREST-path", archivoXML.getCanonicalPath());

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivoXML, true));

            long fechaSistema = System.currentTimeMillis();
            String fechaFormateada = formateadorFecha.format(fechaSistema);
            metrica = "<metrica indicador=\"" + metrica + "\" fecha=\"" + fechaFormateada + "\">" + valor + "</metrica>";

            bufferedWriter.append(metrica);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            Log.e("Error", "Se produjo un error de E/S durante la ejecución del método «adjuntarMetrica» de la clase ConstructorXML.");
        }
    }

    /**
     * Adjunta la métrica especificada por parámetros al archivo XML que
     * almacena las métricas.
     *
     * @param metrica    El nombre de la métrica que se va a adjuntar. Por
     *                   ejemplo, "latenciaMilisegundos".
     * @param valor      El valor correspondiente a la métrica especificada mediante
     *                   la variable «metrica». Siguiendo con el ejemplo que se usó
     *                   para el parámetro anterior, un ejemplo cualquiera de un
     *                   valor apropiado sería «72» (milisegundos).
     * @param tag Un comentario definido por el desarrollador para la
     *                   métrica que se va a adjuntar.
     * @author Ariel Machini
     * @see #adjuntarMetrica(String, String)
     */
    public static void adjuntarMetrica(String metrica, String tag, String valor) {
        File archivoXML = new File(Environment.getExternalStorageDirectory() + "/" + NOMBRE_ARCHIVO + ".xml");

        try {
            if (!archivoXML.exists()) {
                archivoXML.createNewFile();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivoXML, true));

            long fechaSistema = System.currentTimeMillis();
            String fechaFormateada = formateadorFecha.format(fechaSistema);
            metrica = "<metrica indicador=\"" + metrica + "\" fecha=\"" + fechaFormateada + "\" tag=\"" + tag + "\">" + valor + "</metrica>";

            bufferedWriter.append(metrica);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            Log.e("Error", "Se produjo un error de E/S durante la ejecución del método «adjuntarMetrica» de la clase ConstructorXML.");
        }
    }

    /**
     * Adjunta la métrica especificada por parámetros al archivo XML que
     * almacena las métricas.
     *
     * @param idDispositivo UID (IMEI) del dispositivo en el cual se calculó la
     *                      métrica.
     * @param calificacionUsuario La calificación que eligió el usuario para la
     *                            experiencia que le brindó la aplicación en la
     *                            que se realizaron las mediciones.
     * @param metrica El nombre de la métrica que se va a adjuntar. Por
     *                ejemplo, "latenciaMilisegundos".
     * @param valor El valor correspondiente a la métrica especificada mediante
     *              la variable «metrica». Siguiendo con el ejemplo que se usó
     *              para el parámetro anterior, un ejemplo cualquiera de un
     *              valor apropiado sería «72» (milisegundos).
     */
    public static void adjuntarMetrica(String idDispositivo, String calificacionUsuario, String metrica, String valor) {
        File archivoXML = new File(Environment.getExternalStorageDirectory() + "/" + NOMBRE_ARCHIVO + ".xml");

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivoXML, true));

            if (!archivoXML.exists()) {
                archivoXML.createNewFile();

                bufferedWriter.append("<idTest>" + idDispositivo + "</idTest>");
            }



            long fechaSistema = System.currentTimeMillis();
            String fechaFormateada = formateadorFecha.format(fechaSistema);
            metrica = "<indicator \" name=\"" + metrica + "\" date=\"" + fechaFormateada + "\" score=\"" + calificacionUsuario + "\">" + valor + "</indicator>";

            bufferedWriter.append(metrica);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            Log.e("Error", "Se produjo un error de E/S durante la ejecución del método «adjuntarMetrica» de la clase ConstructorXML.");
        }
    }

}
