package location.track.my.spotme;

import android.app.Application;
import android.test.ApplicationTestCase;

import location.track.dbhelper.DBHelper;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testClearDatabse()
    {
        mContext.deleteDatabase(DBHelper.DATABASE_NAME);
    }
}