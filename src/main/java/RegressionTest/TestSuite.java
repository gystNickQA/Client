package RegressionTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Registration.class, //registration (should be first)
        Contacts.class, //create edit  and manage with TestContact
        Task.class, //create edit delete tasks
        Calendar.class, //create edit delete events
        AboutAndFeedback.class, //send feedback
        Profile.class, //edit profile
        Settings.class, //check settings
        InboxSingleMessage.class //send single message and manage with message

})


public class TestSuite {
}
