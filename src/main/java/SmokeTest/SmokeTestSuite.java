package SmokeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import RegressionTest.InboxSingleMessage;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Registration.class, //registration (should be first)
        Contacts.class,
        Task.class,
        Calendar.class,
        Profile.class,
        Notifications.class,
        Settings.class,
        Invite.class,
        AboutAndFeedback.class,
        Inbox.class,
        Labels.class
})


public class SmokeTestSuite {
}
