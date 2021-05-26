public class main {

    private boolean M_is_green;
    boolean forced;
    int minor_state;
    int major_state;
    int low_cycles;
    int low_cycle_bound;
    boolean m_is_green;
    int high_cycles = 0;

    public void fun() {
        if (M_is_green) {
            if (major_state == 1) low_cycles++;
            if (major_state == 2) high_cycles++;
        } else {
            if (minor_state == 1) low_cycles++;
            if (minor_state == 2) high_cycles++;
        }
    }
}