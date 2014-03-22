/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bagusthanatos.inventorysimulation;


import java.util.PriorityQueue;
import java.util.LinkedList;

/**
 *
 * @author BagusNugroho
 */
public class Simulator {
    private PriorityQueue<Event> listCustAr = new PriorityQueue<>();
    private PriorityQueue<Event> listOrderArrival = new PriorityQueue<>();
    private LinkedList<Customer> backLog = new LinkedList<>();
    private double clock;
    private int maxS, minS, stock;
    private double holdingCost,totalCost,shortageCost,orderingCost;
    private final int perH=7,perI=80,perSetup=10,perShortage=20;// merupakan kelipatan 100K, untuk membuat perhitungan lebih simple
    
    public Simulator(int s, int S){
        this.minS=s;
        this.maxS=S;
    }
    public void init(){
        this.clock=0.0;
        this.stock=this.maxS;
        this.holdingCost=0.0;
        this.orderingCost=0.0;
        this.shortageCost=0.0;
        this.totalCost=0.0;

        this.listCustAr.clear();
        this.backLog.clear();
        this.listOrderArrival.clear();
    }
    
    public int getStock(){
        return this.stock;
    }
    public double getTotalCost(){
        return this.totalCost;
    }
    public void setClock(double c){
        this.clock=c;
    }
    public double getClock(){
        return this.clock;
    }
    public void inHoldCost(double i){
        this.holdingCost+=i;
    }
    public void inShortCost(double i){
        this.shortageCost+=i;
    }
    public void inOrderCost(double i){
        this.orderingCost+=i;
    }
    public double hitungHC(int jumMobil,double timeAwal,double timeAkhir){
        return jumMobil*this.perH*(timeAkhir-timeAkhir);
    }
    public double hitungOrderCost(int jumMobil){
        return this.perI*jumMobil+this.perSetup;
    }
    public double hitungShortageCost(int jumMobil){
        return this.perShortage*jumMobil;
    }
    public void reCountCost(){
        this.totalCost=this.orderingCost+this.holdingCost+this.shortageCost;
    }
    
    public void addEvent(Event e){
        if(e.getType()==0) this.listCustAr.add(e);
        else this.listOrderArrival.add(e);
    }
    public Event getNextEvent(){
        Event a,b;
        a=this.listCustAr.peek();
        b=this.listOrderArrival.peek();
        if (a!=null && b!=null) {
            if (b.getTime()>a.getTime()) return this.listCustAr.remove();
            else return this.listOrderArrival.remove();
        }
        else if (b!=null) return this.listOrderArrival.remove();
        else if (a!=null) return this.listCustAr.remove();
        else return null;
    }
    public void doBackLog(){
        while (backLog.size()>0 && this.stock > 0){
            Customer c= this.backLog.removeFirst();
            if (this.stock < c.getJumMobil()){
                this.deStock(this.stock);
                c.setJumMobil(c.getJumMobil()-this.stock);
                this.backLog.addFirst(c);
            }
            else {
                this.deStock(c.getJumMobil());
            }
            System.out.println("C-"+c.getName()+" do backLog:"+(c.getJumMobil()));
        }
    }
    public void addBackLock(Customer c){
        this.backLog.addLast(c);
    }
    public void checkStock(){
        if (this.stock<this.minS && this.listOrderArrival.peek()==null) {
            Customer c= new Customer("-1",this.clock+5+Math.random()*2,this.maxS-this.stock);
            this.addEvent(new Event(c,1,c.getArrivalTime()));
        }
        
    }
    public void restock(int a){
        this.stock+=a;
    }
    public void deStock(int jumMobil){
        this.stock-=jumMobil;
    }
    public int getMaxStock(){
        return this.maxS;
    }
    
}
