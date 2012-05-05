package com.touriXta;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.touriXta.tussamHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class bus extends Activity {
        
        /* Elementos de la UI */
        private Spinner spLinea, spParada;
        private Button btComprobar;
        Context context;
        /** Variable para controlar las veces que se ha seleccionado una línea.
         *  Relevante para n<2 */
        private int vecesSeleccionadaLinea = 0;
        
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3);
        //
        /* Mensaje de advertencia sólo la primera vez que se abre la app */
        // Cadena común para identificar en SharedPrefferences 
        String FIRST_START = "firstStart";
        // SharedPreferences de la aplicación
        SharedPreferences preferences = getSharedPreferences("SeviBus", MODE_PRIVATE);
        // Contiene si es la primera vez que se abre la app o no, por defecto true
        boolean first_start = preferences.getBoolean(FIRST_START, true);
        // Si es la primera vez:
        if(first_start){
                // Muestra el popUp de aviso.
                mostrarAviso();
                // Para que la próxima vez no muestre el motivo 
                preferences.edit().putBoolean(FIRST_START, false).commit();
        }
        /* Spinner de la parada, deshabilitado al abrir la app */
        spParada = (Spinner) findViewById(R.id.spParada);
        spParada.setEnabled(false);
        
        /* Spinner de línea, este tiene más tonterías: */
        spLinea = (Spinner) findViewById(R.id.spLinea);
        // Obtiene un array con las líneas guardadas en Resources
        String[] lineas = getResources().getStringArray(R.array.lineas);
        // Crea un array extendido de las líneas, en cuya primera posición tedrá
        // un indicador de "Selecciona línea"
        String[] lineasExp = new String[lineas.length+1];
        lineasExp[0] = "- Selecciona línea -";
        // y rellena los demás huecos con las líneas
        for(int i=1;i<lineasExp.length;i++){
                lineasExp[i] = lineas[i-1];
        }
        
        // Crea un ArrayAdapter a partir del array de líneas extendido
        ArrayAdapter<String> aa = new ArrayAdapter<String>(bus.this,
                        android.R.layout.simple_spinner_item,lineasExp);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Y lo coloca en el spinner de líneas (elementos al arranque)
                spLinea.setAdapter(aa);         
                /* Listener para la selección de elementos del Spinner */
        spLinea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                /** Cuando se selecciona un elemento */
                        public void onItemSelected(AdapterView<?> parent, View view,
                                        int position, long id) {
                                // Si es la primera vez que se selecciona un elemento
                                /* Al abrir la app se selecciona el primer elemento
                                 * por defecto. Queremos ignorar esta selección.
                                 */
                                if(vecesSeleccionadaLinea==0){
                                        vecesSeleccionadaLinea++;
                                        return;
                                }
                                /* si es la segunda vez que se selecciona, carga la lista de arrys
                                 * para quitar la primera opción ("Selecciona linea") */
                                if(vecesSeleccionadaLinea==1){
                                        /* Hay que recargar el Spinner con sólo las líneas */
                                        // posición del elemento que ha seleccionado el usuario.
                                        int pos = parent.getSelectedItemPosition();
                                        // Crea un ArrayAdapter con las líneas desde Resources
                                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                                            bus.this, R.array.lineas, android.R.layout.simple_spinner_item);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spLinea.setAdapter(adapter);
                                    // Selecciona la misma línea que escogió el usuario en un principio
                                    spLinea.setSelection(pos-1);
                                    // la próxima vez que se seleccione un elemento no hará nada de esto
                                    vecesSeleccionadaLinea++;
                                    return;
                                }
                            // Parsea la línea que ha seleccionado el usuario
                                String linea = parseLineaParada(spLinea.getSelectedItem().toString());
                                // Y carga la lista de paradas según la línea elegida
                                new DescargaParadas().execute(linea);
                        }
                        // no hay más remedio que implementar este método xD
                        public void onNothingSelected(AdapterView<?> arg0) {
                                Toast.makeText(bus.this, "nothing", Toast.LENGTH_SHORT).show();
                        }
                });
        /* Botón para comprobar el tiempo de llegada */
        btComprobar = (Button) findViewById(R.id.btCheck);
        btComprobar.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                                // Parsea la línea selecccionada
                                String linea = parseLineaParada(spLinea.getSelectedItem().toString());
                                String parada;
                                try{
                                        // Parsea la parada seleccionada
                                        parada = parseLineaParada(spParada.getSelectedItem().toString());
                                }catch(NullPointerException npe){
                                        // Si no hay parada seleccionada es porque no hay línea seleccionada
                                        // Avisa al usuario y no hace nada
                                        Toast.makeText(getApplicationContext(), "Selecciona primero una línea", Toast.LENGTH_SHORT).show();
                                        return;
                                }
                                // Descarga y muestra el tiempo de llegada de la línea y parada seleccionadas
                                new DescargaTiempo().execute(linea,parada);
                        }
        }); 
    }
    
    /**
     * Muestra un aviso al iniciar la aplicación por primera vez.
     */
    private void mostrarAviso(){
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setTitle("AVISO! (Que el que avisa no es traidor)")
                .setMessage("Este mensaje no volverá a mostrarse.\n\n" +
                                "Esta es una versión Alfa y puede no funcionar correctamente. " +
                                "Aún tiene muchas cosas pendientes de mejorar, pero si detectas algún fallo te agradecería" +
                                "que me lo dijeras (comentario, email...).\n\n" +
                                "Los datos son extraídos directamente del servicio web de Tussam y la veracidad de los mismos" +
                                "dependerá de éste. No me hago responsable de cualquier inconveniente que te pueda surgir " +
                                "por echar demasiada cuenta a los datos de SeviBus.\n\n" +
                                "Esta es una aplicación no oficial y su autor (vamos, yo) no tiene ninguna relación con Tussam.")
                .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                }
                        })
        .create().show();
    }
    
    /** Crea el menú */
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
                
        }

    /** Cuando se abre un elemento del menú */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                super.onOptionsItemSelected(item);
                switch(item.getItemId()){
                        case R.id.menu_sugerencias:
                                /* Muestra un aviso sobre esta opción. Si el usuario acepta, le envía
                                 * a la aplicación de email. Si no, nada. */
                                AlertDialog.Builder builderSugerencias = new AlertDialog.Builder(this);
                                builderSugerencias.setMessage("He introducido esta opción para " +
                                                "que puedas enviarme cualquier tipo de sugerencia o alguna " +
                                                "función que eches en falta en la aplicación, dado que está aún en " +
                                                "contrucción y estoy abierto a ideas.\n\n" +
                                                "Si pulsas Aceptar se debería abrir tu aplicación de email " +
                                                "con algunos datos preestablecidos para que escribas lo que " +
                                                "quieras. Prefiero esta vía a los " +
                                                "comentarios del Market porque me permite contestar.\n\n" +
                                                "Si usas esta función, muchas gracias por tu ayuda. En caso" +
                                                "contrario, espero que sea porque te gusta como está.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                                                        emailIntent.setType("plain/text");
                                                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"rafa.vazsan@gmail.com"});
                                                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sugerencias para SeviBus");
                                                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Escribe a " +
                                                                        "continuación tu comentario o sugerencia lo más detallado " +
                                                                        "posible.\n------\n");
                                                        startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                                                }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                }
                                        });
                                builderSugerencias.create().show();
                                break;
                }
                return true;
        }
    
        /** Parsea el número de línea o parada en formato ##:***. */
    private String parseLineaParada(String linea){
        return linea.split(":")[0];
    }
    
    /** Descarga y muestra el tiempo de llegada del bus */
    class DescargaTiempo extends AsyncTask<String, Void, Datos> {
        ProgressDialog pd;
               
                protected void onPreExecute() {
                        /* Muestra un progress dialog mientras descarga la información */
                        pd = ProgressDialog.show(bus.this, "", "Descargando tiempos de llegada de Tussam",
                                        true, true, new DialogInterface.OnCancelListener() {
                                /* Cuando se cancela el dialog, se cancela la descarga de la información. */
                                public void onCancel(DialogInterface dialog) {
                                        DescargaTiempo.this.cancel(true);
                                }
                        });
                }

                @Override
                protected Datos doInBackground(String... s){
                        /* Descarga los datos y los devuelve en una clase Datos.
                         * Si hay algún problema devuelve null. */
                
                        Datos dat = new Datos();
                        if(dat.obtenerDatos(s[0], s[1]))
                        {
                                return dat;
                        }
                        Toast.makeText(context, "Esto va mal", Toast.LENGTH_SHORT).show();
                       return null;
                }
                
                @Override
                protected void onPostExecute(Datos datos){
                        /* Al acabar, quita el dialog*/
                        pd.dismiss();
                        // Crea un AlertDialog para mostrar los tiempos.
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(getApplicationContext());
                        /* Define el mensaje del Alert según los resultados */
                        if(datos!=null){
                                /* Si los datos han llegado bien */
                                String mensaje;
                                /* BUS 1 */
                                mensaje = "Primera llegada:\n";
                                if(datos.tiempos[0]>0){
                                        mensaje += datos.tiempos[0]+" minutos | "+datos.distancias[0]+ " metros";
                                }else if(datos.tiempos[0]==0){
                                        mensaje += "Salida inminente";
                                }else{
                                        mensaje += "Sin estimaciones";
                                }
                                mensaje += "\n\n";
                                /* BUS 2 */
                                mensaje += "Siguiente llegada:\n";
                                if(datos.tiempos[1]>0){
                                        mensaje += datos.tiempos[1]+" minutos | "+datos.distancias[1]+ " metros";
                                }else if(datos.tiempos[1]==0){
                                        mensaje += "Salida inminente";
                                }else{
                                        mensaje += "Sin estimaciones";
                                }
                                /* Define el constructor con los datos */
                                builder.setTitle("Ruta: "+datos.nombre).setMessage(mensaje);
                        }else{
                                /* Ha habido un error, define el constructor al respecto */
                                builder.setTitle("ERROR").setMessage("Parada o línea incorrecta (sujeto a disponibilidad)");
                        }
                        /* Haya error o no, coloca el botón de cerrar y muestra el Alert */
                        builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                                
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                }
                        });
                        builder.create().show();
                }
        }

    /** Descaraga la lista de paradas de una línea */
    class DescargaParadas extends AsyncTask<String, Void, List<Parada>> {
        ProgressDialog pd;
               
                protected void onPreExecute() {
                        /* Muestra un progress dialog mientras descarga la información */
                        pd = ProgressDialog.show(bus.this, "", "Descargando lista de paradas de Tussam",
                                        true, true, new DialogInterface.OnCancelListener() {
                                
                                public void onCancel(DialogInterface dialog) {
                                        // Cancela la descarga de paradas
                                        DescargaParadas.this.cancel(true);
                                }
                        });
                }
                
                @Override
                protected void onPostExecute(List<Parada> paradas){
                        // Quita el progress dialog
                        pd.dismiss();
                        // Ordena la lista de paradas por número en orden creciente
                        Collections.sort(paradas);
                        // almacenará los números de las paradas en formato cadena
                        String[] numeroParadas = new String[paradas.size()];
                        /* Recorre la lista de paradas y guarda el número parseado en el array*/ 
                        Iterator<Parada> iter = paradas.iterator();
                        for(int i=0;i<numeroParadas.length;i++){
                                Parada p = iter.next();
                                numeroParadas[i] = String.valueOf(p.getNumero())+": "+p.getNombre();
                        }
                        /* Carga la lista de paradas en su Spinner */
                        ArrayAdapter<String> aa = new ArrayAdapter<String>(bus.this,
                                        android.R.layout.simple_spinner_item,numeroParadas);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spParada.setAdapter(aa);
                        // Habilita el spinner, por si no lo estaba ya.
                        spParada.setEnabled(true);
                }

                @Override
                protected List<Parada> doInBackground(String... linea) {
                        try {
                                /* Define la fuente del XML */
                        URL url = new URL("http://www.infobustussam.com:9005/tussamGO/Resultados?op=lp&ls="+linea[0]);
                        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                        InputSource is = new InputSource(br);                           
                        // create the factory
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        // create a parser
                        SAXParser parser = factory.newSAXParser();
                        // create the reader (scanner)
                        XMLReader xmlreader = parser.getXMLReader();
                        // instantiate our handler
                        tussamHandler th = new tussamHandler(); // <-- el Handler
                        // assign our handler
                        xmlreader.setContentHandler(th);
                        // perform the synchronous parse
                        xmlreader.parse(is);
                        // should be done... let's display our results
                        return th.getParadas();
                }
                catch (Exception e) {
                        //Log.e("SeviBus",e.getMessage());
                        Toast.makeText(bus.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                        return null;
                }
        }
}