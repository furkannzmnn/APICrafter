package org.crafter.util;

public class LandingPage {

    private LandingPage() {
    }
    public static LandingPage showLandingPage() {
        System.out.println("Welcome to Crafter!");
        System.out.println("Crafter is a CLI tool that helps you to create your projects.");
        System.out.println("You can create your projects with Crafter in 3 steps:");
        System.out.println("1. Create a project with Crafter");
        System.out.println("2. Create a repository for your project");
        System.out.println("3. Push your project to your repository");
        System.out.println("Let's start!");
        return new LandingPage();
    }


    public void showLoadingBar() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500); // Yükleme çubuğunun hareketini yavaşlatmak için bekletme yapılır
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\r[" + getLoadingBar(i) + "]");
        }
        System.out.println();
    }

    private String getLoadingBar(int progress) {
        StringBuilder sb = new StringBuilder();
        int barWidth = 20; // Çubuğun genişliği
        int filledBars = (int) (progress / 10.0 * barWidth); // Dolu çubuk sayısı

        for (int i = 0; i < barWidth; i++) {
            if (i < filledBars) {
                sb.append("\u001B[42m "); // Dolu çubuk rengi (yeşil arkaplan)
            } else {
                sb.append("\u001B[47m "); // Boş çubuk rengi (beyaz arkaplan)
            }
        }
        sb.append("\u001B[0m"); // Rengi sıfırla
        return sb.toString();
    }

}
