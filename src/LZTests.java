//These tests are NOT mine. They were written by Jacob Beal.

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by bealjaco on 1/06/17.
 */
@RunWith(Parameterized.class)
public class LZTests {

    @Parameterized.Parameter
    public String check;

    @Parameterized.Parameters(name="{0}")
    public static List<String> data(){
        List<String> ret = new ArrayList<>();

        ret.addAll(Util.permutations("l").collect(Collectors.toList()));
        ret.addAll(Util.permutations("hl").collect(Collectors.toList()));
        ret.addAll(Util.permutations("hel").collect(Collectors.toList()));
        ret.addAll(Util.permutations("hell").collect(Collectors.toList()));
        ret.addAll(Util.permutations("hello").collect(Collectors.toList()));
        ret.addAll(Util.permutations("hellol").collect(Collectors.toList()));
        ret.addAll(Util.permutations("world").collect(Collectors.toList()));

        return ret;
    }

    @Test
    public void run_check(){
        LempelZiv lz = new LempelZiv();
        assertEquals(check,lz.decompress(lz.compress(check)));
    }



}
class Util {
    public static Stream<String> permutations(String str) {
        if (str.isEmpty()) {
            return Stream.of("");
        }
        return IntStream.range(0, str.length())
                .boxed()
                .parallel()
                .flatMap(i ->
                        permutations(str.substring(0, i) + str.substring(i+1)).map(t -> str.charAt(i) + t)
                );
    }
}