package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class Period {

    private LocalDate startDate;
    private LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validatePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalStateException("프로모션 시작일이 종료일보다 클 수 없습니다.");
        }
    }

    public boolean isPeriod() {
        LocalDate now = LocalDate.from(DateTimes.now());
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }
}
