/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bagusthanatos.inventorysimulation;

/**
 *
 * @author BagusNugroho
 */
public class Customer {
    String name;//jika customer adalah toko itu sendiri, saat order mobil, maka isi dengan "-1"
    double arrivalTime;
    int jumMobil;
    int a;
    

    public Customer(String name,double arrivalTime,int jumMobil){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.jumMobil=0;
        
    }

    public void setName(String n){
        this.name = n;
    }

    public void arrive(double tm){
        this.arrivalTime = tm;
        
    }

    public double getArrivalTime(){
        return this.arrivalTime;
    }

    public String getName(){
        return this.name;
    }
    public int getJumMobil(){
        return this.jumMobil;
    }
}
