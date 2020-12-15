package edu.nextstep.lotto.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("LottoNumbers: LottoNumber 목록의 일급 콜렉션 클래스")
class LottoNumbersTest {
	@DisplayName("생성자: LottoNumbers 객체를 정상적으로 생성하여 리턴함")
	@Test
	void generate() {
		LottoNumbers lottoNumbers = new LottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
		assertThat(lottoNumbers)
			.isNotNull()
			.isInstanceOf(LottoNumbers.class)
			.hasNoNullFieldsOrPropertiesExcept("lottoNumbers");
	}

	@DisplayName("생성자: 로또 번호가 개수가 지정된 것보다 많거나 적으면 Exception 발생.")
	@ParameterizedTest
	@ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7"})
	public void generate_shouldException(String numbers) {
		List<Integer> intNumbers = splitToIntList(numbers);
		assertThatThrownBy(() -> new LottoNumbers(intNumbers))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("getRank: Lotto객체가 가진 번호와 당첨번호를 비교해 1,3,4,5등이 되면 LottoRank ENUM을 리턴함. (Optional임)")
	@ParameterizedTest
	@CsvSource(value = {
		"1,2,3,5,4,6:1,2,3,4,5,6:6",
		"1,2,4,5,3,6:1,2,3,4,5,7:5",
		"3,4,5,6,1,2:1,2,3,4,7,8:4",
		"2,3,4,1,5,6:1,2,3,7,8,9:3"
	}, delimiter = ':')
	void getRank(String numbersOne, String numbersTow, int expected) {
		LottoNumbers numbers1 = new LottoNumbers(splitToIntList(numbersOne));
		LottoNumbers numbers2 = new LottoNumbers(splitToIntList(numbersTow));
		assertThat(numbers1.countBySame(numbers2)).isEqualTo(expected);
	}

	private List<Integer> splitToIntList(String numbers) {
		return Arrays.stream(numbers.split(","))
			.map(Integer::parseInt)
			.collect(Collectors.toList());
	}
}