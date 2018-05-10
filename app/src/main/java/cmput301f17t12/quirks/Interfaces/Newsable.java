package cmput301f17t12.quirks.Interfaces;

import java.util.Date;

public interface Newsable {

    String buildNewsHeader();
    String buildDate();
    String buildNewsDescription();
    Date getDate();
}
