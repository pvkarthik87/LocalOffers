package com.kar.localoffers;

import android.content.pm.PackageInfo;
import android.test.ApplicationTestCase;
import android.test.MoreAsserts;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Karthik on 4/12/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest extends ApplicationTestCase<OffersApplication> {

    private OffersApplication application;

    public ApplicationTest() {
        super(OffersApplication.class);
    }

    @Before
    public void setUp() throws Exception {
        createApplication();
        application = getApplication();
        super.setUp();
    }

    @Test
    public void testCorrectVersion() throws Exception {
        PackageInfo info = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        assertNotNull(info);
        MoreAsserts.assertMatchesRegex("\\d\\.\\d", info.versionName);
    }

}
