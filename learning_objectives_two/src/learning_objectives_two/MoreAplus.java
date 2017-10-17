package learning_objectives_two;

public class MoreAplus {
	public static void main(String[] args) {
		CoreCode core_code = new CoreCode();
		System.out.println(core_code.many_aplus("aplus","dog#cat#pigaplus"));
		System.out.println(core_code.many_aplus("aplus","pigs#apluscompsci#food"));
		System.out.println(core_code.many_aplus("aplus","##catgiraffeapluscompsci"));
		System.out.println(core_code.many_aplus("aplus","apluscatsanddoogsaplus###"));
		System.out.println(core_code.many_aplus("aplus","###"));
		System.out.println(core_code.many_aplus("aplus","aplusdog#13337#pigaplusprogram"));
		System.out.println(core_code.many_aplus("aplus","code#H00P#code1234"));
		System.out.println(core_code.many_aplus("aplus","##wowgira77##eplus"));
		System.out.println(core_code.many_aplus("aplus","catsandaplusdogsaplus###"));
		System.out.println(core_code.many_aplus("aplus","7"));
		System.out.println("");
		System.out.println(core_code.many_aplus("aplus","aplusaplus"));
	}
}
class CoreCode {
	public String many_aplus(String arg2,String arg) {
		boolean mode = false;
		int ii = 0;
		String passage = arg2;
		int occurances = 0;
		for (int i = 0;i < arg.length();i++) {
			if (arg.charAt(i) == 'a') {
					mode = true;
					ii = 1;
			}
			else if ((mode == true)&&(passage.charAt(ii) == arg.charAt(i))) {
				if (ii == passage.length() - 1) {
					occurances++;
					mode = false;
				}
				ii++;
			}
			else if (mode == true) {
				mode = false;
			}
		}
		if (occurances > 1) {
			return "yes";
		}
		return "no";
	}
}