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

fn solve() {
	let (R, C) = read!(usize, usize);
	let cs = read!([char]; R);
	let mut grundy = mat![0; R + 1; C + 1; R + 1; C + 1];
	let mut count = 0;
	for i2 in 1..R+1 {
		for j2 in 1..C+1 {
			for i1 in (0..i2).rev() {
				for j1 in (0..j2).rev() {
					let mut gs = vec![];
					let mut ok_i = vec![true; R];
					let mut ok_j = vec![true; C];
					let init = (i1, j1, i2, j2) == (0, 0, R, C);
					for i in i1..i2 {
						for j in j1..j2 {
							if cs[i][j] == '#' {
								ok_i[i] = false;
								ok_j[j] = false;
							}
						}
					}
					for i in i1..i2 {
						for j in j1..j2 {
							if ok_i[i] {
								let g = grundy[i1][j1][i][j2] ^ grundy[i + 1][j1][i2][j2];
								if init && g == 0 {
									count += 1;
								}
								gs.push(g);
							}
							if ok_j[j] {
								let g = grundy[i1][j1][i2][j] ^ grundy[i1][j + 1][i2][j2];
								if init && g == 0 {
									count += 1;
								}
								gs.push(g);
							}
						}
					}
					gs.sort();
					gs.dedup();
					for g in 0.. {
						if g >= gs.len() || gs[g] != g {
							grundy[i1][j1][i2][j2] = g;
							break;
						}
					}
				}
			}
		}
	}
	println!("{}", if grundy[0][0][R][C] == 0 { 0 } else { count });
}

fn run() {
	let T = read!(usize);
	for i in 0..T {
		print!("Case #{}: ", i + 1);
		solve();
	}
}
