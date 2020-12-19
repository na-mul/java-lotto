package edu.nextstep.lotto.domain.sub;

import java.util.Arrays;
import java.util.Optional;

public enum LottoRank {
	FIRST(LottoNumbers.SIZE, 2_000_000_000L),
	SECOND(LottoNumbers.SIZE - 1, 30_000_000L),
	THIRD(LottoNumbers.SIZE - 1, 1_500_000L),
	FOURTH(LottoNumbers.SIZE - 2, 50_000L),
	FIFTH(LottoNumbers.SIZE - 3, 5_000L),
	NONE(0, 0L);

	private final int matchCount;
	private final long reward;

	LottoRank(int matchCount, long reward) {
		this.matchCount = matchCount;
		this.reward = reward;
	}

	public static Optional<LottoRank> valueOf(int matchCount, boolean isMatchBonus) {
		if (matchCount < 0 || matchCount > LottoNumbers.SIZE) {
			return Optional.empty();
		}
		if (matchCount < FIFTH.matchCount) {
			return Optional.of(NONE);
		}
		if (SECOND.matchCount == matchCount && isMatchBonus) {
			return Optional.of(SECOND);
		}
		return Arrays.stream(LottoRank.values())
			.filter(lottoRank -> !lottoRank.equals(LottoRank.SECOND))
			.filter(lottoRank -> lottoRank.matchCount == matchCount)
			.findFirst();
	}

	public int getMatchCount() {
		return matchCount;
	}

	public long getReward() {
		return reward;
	}
}