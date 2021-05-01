#![allow(non_snake_case, dead_code, unused_imports, unused_macros)]

trait SetMinMax {
	fn setmin(&mut self, v: Self) -> bool;
	fn setmax(&mut self, v: Self) -> bool;
}
impl<T> SetMinMax for T where T: PartialOrd {
	fn setmin(&mut self, v: T) -> bool {
		*self > v && { *self = v; true }
	}
	fn setmax(&mut self, v: T) -> bool {
		*self < v && { *self = v; true }
	}
}

macro_rules! mat {
	($($e:expr),*) => { Vec::from(vec![$($e),*]) };
	($($e:expr,)*) => { Vec::from(vec![$($e),*]) };
	($e:expr; $d:expr) => { Vec::from(vec![$e; $d]) };
	($e:expr; $d:expr $(; $ds:expr)+) => { Vec::from(vec![mat![$e $(; $ds)*]; $d]) };
}

fn readln() -> String {
	let mut line = String::new();
	::std::io::stdin().read_line(&mut line).unwrap_or_else(|e| panic!("{}", e));
	line
}

macro_rules! read {
	($($t:tt),*; $n:expr) => {{
		let stdin = ::std::io::stdin();
		let ret = ::std::io::BufRead::lines(stdin.lock()).take($n).map(|line| {
			let line = line.unwrap();
			let mut it = line.split_whitespace();
			_read!(it; $($t),*)
		}).collect::<Vec<_>>();
		ret
	}};
	($($t:tt),*) => {{
		let line = readln();
		#[allow(unused_mut)]
		let mut it = line.split_whitespace();
		_read!(it; $($t),*)
	}};
}

macro_rules! _read {
	($it:ident; [char]) => {
		_read!($it; String).chars().collect::<Vec<_>>()
	};
	($it:ident; [u8]) => {
		Vec::from(_read!($it; String).into_bytes())
	};
	($it:ident; [$t:ty]) => {
		$it.map(|s| s.parse::<$t>().unwrap_or_else(|e| panic!("{}", e))).collect::<Vec<_>>()
	};
	($it:ident; $t:ty) => {
		$it.next().unwrap_or_else(|| panic!("input mismatch")).parse::<$t>().unwrap_or_else(|e| panic!("{}", e))
	};
	($it:ident; $($t:tt),+) => {
		($(_read!($it; $t)),*)
	};
}

fn main() {
	let _ = ::std::thread::Builder::new().name("run".to_string()).stack_size(32 * 1024 * 1024).spawn(run).unwrap().join();
}

fn run() {
	let T = read!(usize);
	for i in 0..T {
		print!("Case #{}: ", i + 1);
		let (from, to) = read!(String, String);
		let out = solve(&from, &to);
		if out == INF {
			println!("IMPOSSIBLE");
		} else {
			println!("{}", out);
		}
	}
}

// fn run() {
// 	use rand::prelude::*;
// 	let mut rng = rand_chacha::ChaCha20Rng::seed_from_u64(4832908);
// 	loop {
// 		let from = rng.gen_range(0, 32);
// 		let to = rng.gen_range(0, 32);
// 		let from = format!("{:b}", from);
// 		let to = format!("{:b}", to);
// 		dbg!(&from, &to);
// 		let out = solve(&from, &to);
// 		let ans = solve2(&from, &to);
// 		assert_eq!(out, ans);
// 	}
// }

const INF: usize = 1000000000;

fn check(from: &[usize], to: &[usize], not: usize) -> bool {
	if not == 0 {
		from == &to[0..from.len()]
	} else {
		for i in 0..from.len() {
			if from[i] == to[i] {
				return false;
			}
		}
		true
	}
}

fn solve2(from: &str, to: &str) -> usize {
	let from = from.chars().map(|a| (a as u8 - b'0') as usize).collect::<Vec<_>>();
	let to = to.chars().map(|a| (a as u8 - b'0') as usize).collect::<Vec<_>>();
	let mut a = 0;
	for c in from {
		a = a * 2 + c;
	}
	let mut b = 0;
	for c in to {
		b = b * 2 + c;
	}
	let mut min = vec![INF; 1 << 20];
	min[a] = 0;
	let mut que = std::collections::VecDeque::new();
	que.push_back(a);
	while let Some(c) = que.pop_front() {
		if c == b {
			return min[c];
		}
		let crt = min[c];
		if c * 2 < min.len() && min[c * 2].setmin(crt + 1) {
			que.push_back(c * 2);
		}
		let mut not = 0;
		let mut i = 0;
		if c == 0 {
			not = 1;
		} else {
			while c >> i != 0 {
				if c >> i & 1 == 0 {
					not |= 1 << i;
				}
				i += 1;
			}
		}
		if min[not].setmin(crt + 1) {
			que.push_back(not);
		}
	}
	INF
}

fn solve(from: &str, to: &str) -> usize {
	let from = from.chars().map(|a| (a as u8 - b'0') as usize).collect::<Vec<_>>();
	let to = to.chars().map(|a| (a as u8 - b'0') as usize).collect::<Vec<_>>();
	let mut min = INF;
	for num_double in 0..=to.len() {
		let tail = to.len() - num_double;
		if tail > from.len() {
			continue;
		}
		let num_delete = from.len() - tail;
		for odd in 0..2 {
			if !check(&from[from.len() - tail..], &to, odd) {
				continue;
			}
			let mut dp = mat![INF; num_delete + 1; num_double + 1; 2]; // [deleted][double][not]
			dp[0][0][0] = 0;
			for deleted in 0..=num_delete {
				for double in 0..=num_double {
					for _ in 0..2 {
						for not in 0..2 {
							let crt = dp[deleted][double][not];
							if crt == INF {
								continue;
							}
							// double
							if double < num_double {
								if double == 0 && deleted == from.len() - 1 && from[from.len() - 1] == not {
								} else {
									if (not != odd) ^ (to[to.len() - num_double + double] == 0) {
										dp[deleted][double + 1][not].setmin(crt + 1);
									}
								}
							}
							// not
							let mut d = 0;
							while deleted + d < from.len() && from[deleted + d] != not {
								d += 1;
							}
							if double == 0 && deleted + d == from.len() {
								d -= 1;
							} else if deleted + d == from.len() && double > 0 && to[tail] != odd ^ not {
								continue;
							}
							if deleted + d <= num_delete {
								dp[deleted + d][double][1 - not].setmin(crt + 1);
							}
						}
					}
				}
			}
			min.setmin(dp[num_delete][num_double][odd]);
		}
	}
	min
}
