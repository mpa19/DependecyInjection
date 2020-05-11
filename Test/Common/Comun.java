package Common;

import implementations.ImplementationC1;
import implementations.ImplementationD1;
import interfaces.InterfaceC;
import interfaces.InterfaceD;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Comun {

    public void testD(InterfaceD d) {
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.getI(), is(42));
    }

    public void testC(InterfaceC c) {
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.getS(), is("CONSTANT"));
    }

}
