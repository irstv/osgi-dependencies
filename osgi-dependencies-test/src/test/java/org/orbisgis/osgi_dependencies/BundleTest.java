/**
 * H2GIS is a library that brings spatial support to the H2 Database Engine
 * <http://www.h2database.com>. H2GIS is developed by CNRS
 * <http://www.cnrs.fr/>.
 *
 * This code is part of the H2GIS project. H2GIS is free software;
 * you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * version 3.0 of the License.
 *
 * H2GIS is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details <http://www.gnu.org/licenses/>.
 *
 *
 * For more information, please consult: <http://www.h2gis.org/>
 * or contact directly: info_at_h2gis.org
 */


package org.orbisgis.osgi_dependencies;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.UrlProvisionOption;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import javax.inject.Inject;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * {@see http://felix.apache.org/site/apache-felix-ipojo-junit4osgi-tutorial.html}
 * @author Nicolas Fortin
 * @author Sylvain PALOMINOS
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class BundleTest {
    @Inject
    BundleContext context;
    private File bundleFolder = new File("target/bundle");

    @Configuration
    public Option[] config() throws MalformedURLException {
        List<Option> options = new ArrayList<Option>();
        options.addAll(Arrays.asList(systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("WARN"),
                getBundle("org.osgi.compendium"),
                getBundle("commons-vfs2-osgi-1.0"),
                getBundle("jcommon-osgi-1.0.23"),
                getBundle("jfreechart-osgi-1.0.19"),
                getBundle("jts-osgi-1.13"),
                getBundle("netlib-all-osgi-1.0"),
                getBundle("netlib-util-osgi-1.0"),
                getBundle("jniloader-osgi-1.0"),
                getBundle("postgresql-osgi-9.1-901-1.jdbc4"),
                getBundle("servlet-api-osgi-1.0.2"),
                getBundle("trove-osgi-1.0.2"),
                getBundle("weka-osgi-3.8.0"),
                getBundle("aether-api-osgi-1.0"),
                getBundle("aether-spi-osgi-1.0"),
                getBundle("aether-connector-basic-osgi-1.0"),
                getBundle("aether-util-osgi-1.0"),
                getBundle("aether-impl-osgi-1.0"),
                getBundle("javax.inject-osgi-1.0"),
                getBundle("org.eclipse.sisu.inject-osgi-1.0"),
                getBundle("org.eclipse.sisu.plexus-osgi-1.0"),
                getBundle("sisu-guice-osgi-1.0"),
                getBundle("sisu-guava-osgi-1.0"),
                getBundle("aether-transport-file-osgi-1.0"),
                getBundle("aether-transport-http-osgi-1.0"),
                getBundle("httpclient-osgi-1.0"),
                getBundle("httpcore-osgi-1.0"),
                getBundle("commons-codec-osgi-1.0"),
                getBundle("codemodel-osgi-1.0"),
                getBundle("commons-compress-osgi-1.0"),
                getBundle("commons-logging-osgi-1.0"),
                getBundle("xz-osgi-1.0"),
                getBundle("commons-math-osgi-1.0"),
                getBundle("joda-time-osgi-1.0"),
                getBundle("joda-convert-osgi-1.0"),
                getBundle("renjin-osgi-1.0"),
                getBundle("maven-repository-metadata-osgi-1.0"),
                getBundle("maven-aether-provider-osgi-1.0"),
                getBundle("maven-model-osgi-1.0"),
                getBundle("maven-settings-osgi-1.0"),
                getBundle("maven-settings-builder-osgi-1.0"),
                getBundle("maven-scm-api-osgi-1.0"),
                getBundle("maven-scm-provider-svnexe-osgi-1.0"),
                getBundle("maven-scm-provider-svn-commons-osgi-1.0"),
                getBundle("plexus-utils-osgi-1.0"),
                getBundle("plexus-cipher-osgi-1.0"),
                getBundle("plexus-classworlds-osgi-1.0"),
                getBundle("plexus-component-annotations-osgi-1.0"),
                getBundle("plexus-interpolation-osgi-1.0"),
                getBundle("plexus-sec-dispatcher-osgi-1.0"),
                getBundle("airline-osgi-1.0"),
                getBundle("aopalliance-osgi-1.0"),
                getBundle("guava-osgi-1.0"),
                getBundle("regexp-osgi-1.0"),
                getBundle("asm-osgi-1.0"),
                getBundle("cdi-api-osgi-1.0"),
                getBundle("jackson-annotations-osgi-1.0"),
                getBundle("jackson-core-osgi-1.0"),
                getBundle("jackson-databind-osgi-1.0"),
                junitBundles()));
        //options.addAll(getBundles());
        return options(options.toArray(new Option[options.size()]));
    }

    private UrlProvisionOption getBundle(String bundleName) throws MalformedURLException {
        return bundle(new File(bundleFolder, bundleName+".jar").toURI().toURL().toString());
    }

    private List<Option> getBundles() {
        List<Option> bundles = new ArrayList<Option>();
        File bundleFolder = new File("target/bundle");
        for(File bundle : bundleFolder.listFiles()) {
            try {
                bundles.add(bundle(bundle.toURI().toURL().toString()).noStart());
            } catch (MalformedURLException ex) {
                // Ignore
            }
        }
        return bundles;
    }

    /**
     * Validate integration of built-in bundles.
     */
    @Test
    public void testBuiltInBundleActivation() throws Exception {
        System.out.println("Built-In bundle list :");
        System.out.println("ID\t\tState\tBundle name");
        for (Bundle bundle : context.getBundles()) {
            System.out.println(
                    "[" + String.format("%02d", bundle.getBundleId()) + "]\t"
                            + getStateString(bundle.getState()) + "\t"
                            + bundle.getSymbolicName()+"["+bundle.getVersion()+"]");
            // Print services
            ServiceReference[] refs = bundle.getRegisteredServices();
            if(refs!=null) {
                for(ServiceReference ref : refs) {
                    String refDescr = ref.toString();
                    if(!refDescr.contains("org.osgi") && !refDescr.contains("org.apache")) {
                        System.out.println(
                                "\t\t\t\t" + ref);
                    }
                }
            }
        }
    }

    private String getStateString(int i) {
        switch (i) {
            case Bundle.ACTIVE:
                return "Active   ";
            case Bundle.INSTALLED:
                return "Installed";
            case Bundle.RESOLVED:
                return "Resolved ";
            case Bundle.STARTING:
                return "Starting ";
            case Bundle.STOPPING:
                return "Stopping ";
            default:
                return "Unknown  ";
        }
    }

    @Test
    public void checkResolveState() throws BundleException {
        for (Bundle bundle : context.getBundles()) {
            if(bundle.getState() == Bundle.INSTALLED) {
                throw new BundleException("Bundle "+bundle.getSymbolicName()+" not resolved");
            }
        }
    }
}
