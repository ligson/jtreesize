package org.ligson.jtreesize;


public class App {


    public static void main(String[] args) {
        JWin jWin = new JWin();
        jWin.setVisible(true);
        //CompletableFuture.supplyAsync(() -> calcDirSize(file), pool).whenComplete((aLong, throwable) -> System.out.println("目录大小" + aLong));
    }
}
