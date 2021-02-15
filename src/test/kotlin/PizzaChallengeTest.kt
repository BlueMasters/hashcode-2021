import com.github.bluemasters.hashcode.OutputManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Path

class PizzaChallengeTest {

    @Test
    fun `test a_example is properly loaded`() {
        val challenge = PizzaChallenge(Path.of("in/a_example"), OutputManager())

        assertThat(challenge.pizzas)
            .hasSize(5)
        assertThat(challenge.pizzas.last().ingredients)
            .hasSize(2)
            .containsExactly("chicken", "basil")
        assertThat(challenge.teamSizes())
            .hasSize(3)
            .containsExactlyEntriesOf(mapOf(2 to 1, 3 to 2, 4 to 1))
        assertThat(challenge.deliveries)
            .isNotNull
            .isEmpty()
    }
}