package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 1
//        Random generator = new Random();
//        List<Integer> array = IntStream
//                .generate(() -> generator.nextInt(0, 20))
//                .boxed()
//                .limit(20)
//                .collect(Collectors.toCollection(ArrayList::new));
//
//        System.out.println(array);
//        SortUtils.quickSort(array);
//        System.out.println(array);

        // 2
//        WorkerThread thread = new WorkerThread();
//        thread.start();
//
//        generate(() -> (Runnable) () -> {
//                    try {
//                        Thread.sleep(1000);
//                        System.out.println(Thread.currentThread().getName() + ": finished");
//                    } catch (InterruptedException ignored) {
//
//                    }
//                })
//                .limit(5)
//                .forEach(thread::execute);
//
//        Thread.sleep(10000);
//        thread.interrupt();
        // 3

        String text = "Реализуйте алгоритм Хаффмана для компрессии данных".toLowerCase();
        HuffmanCoder coder = new HuffmanCoder();

        HuffmanResult result = coder.encode(text);
        System.out.println(result.getCodes());
        System.out.println(result.getText());

        String original = coder.decode(result.getText(), result.getHuffmanTree());
        System.out.println(original);
    }
}