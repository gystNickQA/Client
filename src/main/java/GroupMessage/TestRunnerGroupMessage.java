package GroupMessage;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestRunnerGroupMessage {
    public static void main(String[] args) {
        Executor start = Executors.newFixedThreadPool(2);
        Runnable suit1 = new Runnable() {
            @Override
            public void run() {
                Result result = JUnitCore.runClasses(TestSuiteGroupMessageSender.class);
                for (Failure failure : result.getFailures()) {
                    System.out.println(failure.toString());
                }
                System.out.println(result.wasSuccessful());
            }
        };

        Runnable suit2 = new Runnable() {

            @Override
            public void run() {
                Result result = JUnitCore.runClasses(TestSuiteGroupMessageReceiver.class);
                for (Failure failure : result.getFailures()) {
                    System.out.println(failure.toString());
                }
                System.out.println(result.wasSuccessful());
            }
        };
        start.execute(suit2);
        start.execute(suit1);

    }
}
