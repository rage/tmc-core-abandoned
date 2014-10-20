package fi.helsinki.cs.tmc.client.core.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourseTest {

    private Course course;

    @Before
    public void setUp() {

        course = new Course();
    }

    @Test
    public void testGetSpywareUrlsDoesntReturnNull() {

        assertNotNull(course.getSpywareUrls());
    }

    @Test
    public void testGetUnlockablesDoesntReturnNull() {

        assertNotNull(course.getUnlockables());
    }

    @Test
    public void testGetExercisesDoesntReturnNull() {

        assertNotNull(course.getExercises());
    }

    @Test
    public void testId() {

        course.setId(1);
        assertEquals(1, course.getId());
    }

    @Test
    public void testName() {

        course.setName("name");
        assertEquals("name", course.getName());
    }

    @Test
    public void testDetailsUrl() {

        course.setDetailsUrl("detailsUrl");
        assertEquals("detailsUrl", course.getDetailsUrl());
    }

    @Test
    public void testUnlockUrl() {

        course.setUnlockUrl("unlockUrl");
        assertEquals("unlockUrl", course.getUnlockUrl());
    }

    @Test
    public void testReviewUrl() {

        course.setReviewsUrl("reviewsUrl");
        assertEquals("reviewsUrl", course.getReviewsUrl());
    }

    @Test
    public void testCometUrl() {

        course.setCometUrl("cometUrl");
        assertEquals("cometUrl", course.getCometUrl());
    }

    @Test
    public void testSpywareUrl() {

        final List<String> spywareUrls = new ArrayList<String>();
        course.setSpywareUrls(spywareUrls);
        assertEquals(spywareUrls, course.getSpywareUrls());
    }

    @Test
    public void testExerciseLoaded() {

        course.setExercisesLoaded(true);
        assertEquals(true, course.isExercisesLoaded());
    }

    @Test
    public void testUnlockables() {

        final List<String> unlockables = new ArrayList<String>();
        course.setUnlockables(unlockables);
        assertEquals(unlockables, course.getUnlockables());
    }

    @Test
    public void testExercises() {

        final List<Exercise> exercises = new ArrayList<Exercise>();
        course.setExercises(exercises);
        assertEquals(exercises, course.getExercises());
    }

    @Test
    public void toStringReturnsName() {

        course.setName("CourseName");
        assertEquals("CourseName", course.toString());
    }

    @Test
    public void getDownloadableExercisesDownloadsAll() {

        final Exercise ex1 = new Exercise();
        final Exercise ex2 = new Exercise();
        final Exercise ex3 = new Exercise();

        final List<Exercise> exList = new ArrayList<>();

        exList.add(ex1);
        exList.add(ex2);
        exList.add(ex3);

        course.setExercises(exList);

        assertEquals(course.getDownloadableExercises().size(), 3);
    }

    @Test
    public void getDownloadableExercisesDownloadsOnlyDownloadable() {

        final Exercise ex1 = new Exercise();
        final Exercise ex2 = new Exercise();
        final Exercise ex3 = new Exercise();

        final Date d = new Date();

        d.setTime(0);

        ex1.setDeadline(d);

        final List<Exercise> exList = new ArrayList<>();

        exList.add(ex1);
        exList.add(ex2);
        exList.add(ex3);

        course.setExercises(exList);

        assertEquals(course.getDownloadableExercises().size(), 2);
    }

    @Test
    public void getDownloadableExercisesDownloadsOnlyDownloadableAndUpdatedAndNotComplete() {

        final Exercise ex1 = new Exercise();
        final Exercise ex2 = new Exercise();
        final Exercise ex3 = new Exercise();

        final Date d = new Date();

        d.setTime(0);

        ex1.setDeadline(d);
        ex2.setDeadline(d);
        ex2.setUpdateAvailable(true);
        ex3.setDeadline(d);
        ex3.setCompleted(true);

        final List<Exercise> exList = new ArrayList<>();

        exList.add(ex1);
        exList.add(ex2);
        exList.add(ex3);

        course.setExercises(exList);

        assertEquals(course.getDownloadableExercises().size(), 1);
    }
}