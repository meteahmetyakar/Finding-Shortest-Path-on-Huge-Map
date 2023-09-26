package test;

/**
 * it starts test cases
 */
public class Main {
    public static void main(String args[]){
        new Thread(new TestCases("Map01.txt", 500, 500)).start();
        new Thread(new TestCases("Map02.txt", 500, 500)).start();
        new Thread(new TestCases("Map03.txt", 500, 500)).start();
        new Thread(new TestCases("Map04.txt", 500, 500)).start();
        new Thread(new TestCases("Map05.txt", 500, 500)).start();
        new Thread(new TestCases("Map06.txt", 500, 500)).start();
        new Thread(new TestCases("Map07.txt", 500, 500)).start();
        new Thread(new TestCases("Map08.txt", 500, 500)).start();
        new Thread(new TestCases("Map09.txt", 500, 500)).start();
        new Thread(new TestCases("Map10.txt", 500, 500)).start();
        new Thread(new TestCases("pisa.txt", 500, 500)).start();
        new Thread(new TestCases("tokyo.txt", 500, 500)).start();
        new Thread(new TestCases("triumph.txt", 500, 500)).start();
        new Thread(new TestCases("vatican.txt", 500, 500)).start();
        new Thread(new TestCases("dnm.txt", 500, 500)).start();

    }
}

