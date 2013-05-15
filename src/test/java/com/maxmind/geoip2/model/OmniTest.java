package com.maxmind.geoip2.model;

import com.maxmind.geoip2.record.*;
import java.util.*;
import org.json.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class OmniTest extends TestCase {
    Omni city;

    public OmniTest(String testName) {
        super(testName);
        JSONObject jcity = createJSONCity();
        city = new Omni(jcity);

    }

    public static Test suite() {
        return new TestSuite(OmniTest.class);
    }

    private JSONObject createJSONCity() {
        String str = "{" + "\"city\":{" + "\"confidence\":76,"
                + "\"geoname_id\":9876," + "\"names\":{"
                + "\"en\":\"Minneapolis\"" + "}" + "}," +

                "\"continent\":{" + "\"continent_code\":\"NA\","
                + "\"geoname_id\":42," + "\"names\":{"
                + "\"en\":\"North America\"" + "}" + "}," +

                "\"country\":{" + "\"confidence\":99," + "\"iso_code\":\"US\","
                + "\"geoname_id\":1," + "\"names\":{"
                + "\"en\":\"United States of America\"" + "}" + "}," +

                "\"location\":{" + "\"accuracy_radius\":1500,"
                + "\"latitude\":44.98," + "\"longitude\":93.2636,"
                + "\"metro_code\":765," + "\"postal_code\":\"55401\","
                + "\"postal_confidence\":33,"
                + "\"time_zone\":\"America/Chicago\"" + "},"
                + "\"registered_country\":{" + "\"geoname_id\":2,"
                + "\"iso_code\":\"CA\"," + "\"names\":{" + "\"en\":\"Canada\""
                + "}" + "}," + "\"represented_country\":{"
                + "\"geoname_id\":3," + "\"iso_code\":\"GB\"," + "\"names\":{"
                + "\"en\":\"United Kingdom\"" + "},"
                + "\"type\":\"C<military>\"" + "}," + "\"subdivisions\":[{"
                + "\"confidence\":88," + "\"geoname_id\":574635,"
                + "\"iso_code\":\"MN\"," + "\"names\":{"
                + "\"en\":\"Minnesota\"" + "}" + "}" + "]," + "\"traits\":{"
                + "\"autonomous_system_number\":1234,"
                + "\"autonomous_system_organization\":\"AS Organization\","
                + "\"domain\":\"example.com\"," + "\"ip_address\":\"1.2.3.4\","
                + "\"is_anonymous_proxy\":true," + "\"isp\":\"Comcast\","
                + "\"organization\":\"Blorg\"," + "\"user_type\":\"college\""
                + "}" + "}";
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            fail(e.getMessage());
            return null;
        }
    }

    public void testSubdivisionsList() {
        ArrayList<Subdivision> subdivisionsList = city.getSubdivisionsList();
        assertNotNull("city.getSubdivisionsList returns null", subdivisionsList);
        if (subdivisionsList.size() == 0) {
            fail("subdivisionsList is empty");
        }
        Subdivision subdivision = subdivisionsList.get(0);
        assertEquals("subdivision.getConfidence() does not return 88",
                new Integer(88), subdivision.getConfidence());
        assertEquals("subdivision.getGeoNameId() does not return 574635",
                574635, (int) subdivision.getGeoNameId());
        assertEquals("subdivision.getCode() does not return MN", "MN",
                subdivision.getIsoCode());
    }

    public void testTraits() {
        Traits traits = city.getTraits();

        assertNotNull("city.getTraits() returns null", traits);
        assertEquals("traits.getAutonomousSystemNumber() does not return 1234",
                new Integer(1234), traits.getAutonomousSystemNumber());
        assertEquals(
                "traits.getAutonomousSystemOrganization() does not return AS Organization",
                "AS Organization", traits.getAutonomousSystemOrganization());
        assertEquals(
                "traits.getAutonomousSystemOrganization() does not return example.com",
                "example.com", traits.getDomain());
        assertEquals("traits.getIpAddress() does not return 1.2.3.4",
                "1.2.3.4", traits.getIpAddress());
        assertEquals("traits.isAnonymousProxy() returns false", true,
                traits.isAnonymousProxy());
        assertEquals("traits.getIsp() does not return Comcast", "Comcast",
                traits.getIsp());
        assertEquals("traits.getOrganization() does not return Blorg", "Blorg",
                traits.getOrganization());
        assertEquals("traits.getUserType() does not return userType",
                "college", traits.getUserType());
    }

    public void testLocation() {

        Location location = city.getLocation();

        assertNotNull("city.getLocation() returns null", location);

        assertEquals("location.getAccuracyRadius() does not return 1500",
                new Integer(1500), location.getAccuracyRadius());

        double latitude = location.getLatitude();
        assertEquals("location.getLatitude() does not return 44.98", 44.98,
                latitude, 0.1);
        double longitude = location.getLongitude();
        assertEquals("location.getLongitude() does not return 93.2636",
                93.2636, longitude, 0.1);
        assertEquals("location.getMetroCode() does not return 765",
                new Integer(765), location.getMetroCode());

        assertEquals("location.getPostalCode() does not return 55401", "55401",
                location.getPostalCode());
        assertEquals("location.getPostalConfidence() does not return 33",
                new Integer(33), location.getPostalConfidence());
        assertEquals("location.getTimeZone() does not return America/Chicago",
                "America/Chicago", location.getTimeZone());
    }

    public void testRepresentedCountry() {
        assertNotNull("city.getRepresentedCountry() returns null",
                city.getRepresentedCountry());

        assertEquals(
                "city.getRepresentedCountry().getType() does not return C<military>",
                "C<military>", city.getRepresentedCountry().getType());
    }
}
