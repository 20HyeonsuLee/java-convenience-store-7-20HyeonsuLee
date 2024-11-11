package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.exception.InputFormatException;

public class Period {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validatePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InputFormatException();
        }
    }

    public boolean isPeriod() {
        LocalDate now = LocalDate.from(DateTimes.now());
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }
}
