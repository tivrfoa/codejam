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

pub fn next_permutation<T>(a: &mut [T]) -> bool where T: PartialOrd {
	let n = a.len();
	for i in (1..n).rev() {
		if a[i - 1] < a[i] {
			let mut j = n - 1;
			while a[i - 1] >= a[j] {
				j -= 1;
			}
			a.swap(i - 1, j);
			a[i..n].reverse();
			return true;
		}
	}
	a.reverse();
	false
}

use std::io::*;

fn solve(F: usize) {
	let mut ans = vec![];
	let mut a = vec![true; 119];
	let mut used = vec![false; 5];
	let mut query = 0;
	for i in 0..5 {
		let mut count = vec![0; 5];
		let mut cs = vec![0; 119];
		for j in 0..119 {
			if a[j] {
				query += 1;
				println!("{}", j * 5 + i + 1);
				let c = read!([u8])[0] - b'A';
				cs[j] = c;
				count[c as usize] += 1;
			}
		}
		let mut c = 0;
		let mut min = 10000;
		for j in 0..5 {
			if !used[j] && min > count[j] {
				min = count[j];
				c = j as u8;
			}
		}
		used[c as usize] = true;
		ans.push(c);
		for j in 0..119 {
			if a[j] && cs[j] != c {
				a[j] = false;
			}
		}
	}
	dbg!(query);
	println!("{}", ans.into_iter().map(|c| (c + b'A') as char).collect::<String>());
	std::io::stdout().flush();
	let ret = read!(String);
	if ret != "Y" {
		std::process::exit(0);
	}
}

fn run() {
	let (T, F) = read!(usize, usize);
	for i in 0..T {
		solve(F);
	}
}
