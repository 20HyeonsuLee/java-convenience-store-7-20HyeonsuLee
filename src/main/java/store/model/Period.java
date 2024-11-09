package store.model;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
