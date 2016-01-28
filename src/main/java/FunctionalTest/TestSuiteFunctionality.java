package FunctionalTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventRecurrence.class, //test recurrence. Make sure that all calendar are enable
        MessageAttachments.class, //test event attachment and contact attachment in messages
        FileAttachments.class, //test file attachments in messages
        TypesOfImages.class, //test different types of images in messages
        ShareContact.class, //test ability to share contact
        ScheduleMessage.class, //create schedule message
        CheckSwipes.class, //need be at least on task, event, contact, dialog with message to start
        CheckSelections.class, //need be at least on task, event, contact, dialog with message to start
        TimeSelect.class, //test time selection on task
        ChangePhoto.class, //change photo in contact
        Archiving.class, //test archiving 1 day need to be at least one old dialog with message
        LoginAutomatically.class
}
)
public class TestSuiteFunctionality {
}
