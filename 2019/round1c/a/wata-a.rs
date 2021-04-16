#![allow(non_snake_case, unused)]
use std::collections::*;

////////// missing functions in 1.24.1 //////////
macro_rules! dbg {
	($v:expr) => {{
		let val = $v;
		eprintln!("[{}:{}] {} = {:?}", file!(), line!(), stringify!($v), val);
		val
	}}
}
/////////////////////////////////////////////////
pub trait SetMinMax {
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

macro_rules! ok {
	($a:ident$([$i:expr])*.$f:ident()$(@$t:ident)*) => {
		$a$([$i])*.$f($($t),*)
	};
	($a:ident$([$i:expr])*.$f:ident($e:expr$(,$es:expr)*)$(@$t:ident)*) => { {
		let t = $e;
		ok!($a$([$i])*.$f($($es),*)$(@$t)*@t)
	} };
}

pub fn readln() -> String {
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
	($it:ident; usize1) => {
		$it.next().unwrap_or_else(|| panic!("input mismatch")).parse::<usize>().unwrap_or_else(|e| panic!("{}", e)) - 1
	};
	($it:ident; [usize1]) => {
		$it.map(|s| s.parse::<usize>().unwrap_or_else(|e| panic!("{}", e)) - 1).collect::<Vec<_>>()
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

pub fn main() {
	let _ = ::std::thread::Builder::new().name("run".to_string()).stack_size(32 * 1024 * 1024).spawn(run).unwrap().join();
}

fn solve() -> Option<String> {
	let LEN = 500;
	let cs = vec!['R', 'P', 'S'];
	let A = read!(usize);
	let ps = read!([char]; A);
	let mut progs = vec![vec![]; A];
	for i in 0..A {
		for j in 0..ps[i].len() {
			for k in 0..3 {
				if cs[k] == ps[i][j] {
					progs[i].push(k);
					break;
				}
			}
		}
	}
	let mut a = vec![];
	let mut win = vec![false; A];
	for i in 0..LEN {
		let mut has = vec![false; 3];
		for j in 0..A {
			if !win[j] {
				has[progs[j][i % progs[j].len()]] = true;
			}
		}
		if has[0] && has[1] && has[2] {
			return None;
		}
		for c in 0..3 {
			if has[c] && !has[(c + 1) % 3] && !has[(c + 2) % 3] {
				a.push(cs[(c + 1) % 3]);
				return Some(a.into_iter().collect());
			}
		}
		for c in 0..3 {
			if has[c] && !has[(c + 2) % 3] {
				a.push(cs[(c + 1) % 3]);
				for j in 0..A {
					if !win[j] && progs[j][i % progs[j].len()] == c {
						win[j] = true;
					}
				}
				break;
			}
		}
	}
	// for len in 1..(LEN + 1) {
	// 	let mut r: Vec<_> = (0..A).collect();
	// 	for j in 0..(len * LEN + 1) {
	// 		let c = a[j % len];
	// 	}
	// 	if r.len() == 0 {
	// 		return Some(a[0..len + 1].into_iter().collect());
	// 	}
	// }
	None
}

fn run() {
	let T = read!(usize);
	for i in 0..T {
		print!("Case #{}: ", i + 1);
		if let Some(ans) = solve() {
			println!("{}", ans);
		} else {
			println!("IMPOSSIBLE");
		}
	}
}
