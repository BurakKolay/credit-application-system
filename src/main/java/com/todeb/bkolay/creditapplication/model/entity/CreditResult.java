package com.todeb.bkolay.creditapplication.model.entity;

public enum CreditResult {
    RESULT_IS_AWAITED(0),
    DENIED(500),            // Kredi skoru 500den aşağıdaysa onaylanmayacak.
    ACCEPTED(1000);

    private Integer creditScoreLimit;

    CreditResult(Integer creditScoreLimit) {
        this.creditScoreLimit = creditScoreLimit;
    }


    public Integer getCreditScoreLimit() {
        return creditScoreLimit;
    }

    public void setCreditScoreLimit(Integer creditScoreLimit) {
        this.creditScoreLimit = creditScoreLimit;
    }
}
