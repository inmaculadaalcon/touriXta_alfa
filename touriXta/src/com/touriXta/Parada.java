package com.touriXta;


public class Parada implements Comparable<Parada> {

        private String nombre, latitud, longitud;
        private int numero;
        
        public Parada(int num, String  lat, String  lng){
                this.numero = num;
                this.latitud = lat;
                this.longitud = lng;
        }
        
        public void setNombre(String nom){
                this.nombre = nom;
        }
        
        public Integer getNumero(){
                return this.numero;
        }
        public String getNombre(){
                return this.nombre;
        }
        public String getLatitud(){
                return this.latitud;
        }
        public String getLongitud(){
                return this.longitud;
        }

        public int compareTo(Parada otherParada) {
                return Integer.valueOf(this.getNumero()).compareTo(((Parada)otherParada).getNumero());
        }

}