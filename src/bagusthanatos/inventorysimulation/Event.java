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
public class Event implements Comparable{
    int type; // 0 = customer arrival, 1 = order arrival;
    double time; // waktu terjadinya event
    Customer customer; // customer yang memesan, nama dijadikan -1 kalo yang mesan adalah si Dealer

    public Event(Customer c, int t, double tm){
        this.customer = c;
        this.time = tm;
        this.type = t;
    }

    public int getType(){
        return this.type;
    }

    public Customer getCustomer(){
        return customer;
    }

    public double getTime(){
        return this.time;
    }
    @Override
    public int compareTo(Object ob){
        Event e = (Event) ob;
        if(this.time < e.getTime()){
            return -1;
        }
        if(this.time > e.getTime()){
            return 1;
        }
        return 0;
    }
}
