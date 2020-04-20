#include <bits/stdc++.h>
using namespace std;
#define sim template < class c
#define ris return * this
#define dor > debug & operator <<
#define eni(x) sim > typename \
  enable_if<sizeof dud<c>(0) x 1, debug&>::type operator<<(c i) {
sim > struct rge { c b, e; };
sim > rge<c> range(c i, c j) { return rge<c>{i, j}; }
sim > auto dud(c* x) -> decltype(cerr << *x, 0);
sim > char dud(...);
struct debug {
#ifdef LOCAL
~debug() { cerr << endl; }
eni(!=) cerr << boolalpha << i; ris; }
eni(==) ris << range(begin(i), end(i)); }
sim, class b dor(pair < b, c > d) {
  ris << "(" << d.first << ", " << d.second << ")";
}
sim dor(rge<c> d) {
  *this << "[";
  for (auto it = d.b; it != d.e; ++it)
    *this << ", " + 2 * (it == d.b) << *it;
  ris << "]";
}
#else
sim dor(const c&) { ris; }
#endif
};
#define imie(...) " [" << #__VA_ARGS__ ": " << (__VA_ARGS__) << "] "
 
using ll = long long;



void test_case() {
	int n;
	ll m;
	scanf("%d%lld", &n, &m);
	vector<ll> a(n);
	for(int i = 0; i < n; ++i) {
		scanf("%lld", &a[i]);
	}
	long long sure = 0;
	for(int bit = 52; bit >= 0; --bit) {
		int cnt = 0;
		for(ll x : a) {
			if(x & (1LL << bit)) {
				++cnt;
			}
		}
		sure += (1LL << bit) * min(cnt, n - cnt);
	}
	long long best = 0;
	long long already = sure;
	debug() << imie(sure) imie(m);
	for(int bit = 52; bit >= 0; --bit) {
		int cnt = 0;
		for(ll x : a) {
			if(x & (1LL << bit)) {
				++cnt;
			}
		}
		long long hack = (1LL << bit) * min(cnt, n - cnt);
		long long maybe = already + (1LL << bit) * (n - cnt) - hack;
		if(maybe <= m) {
			already = maybe;
			best ^= 1LL << bit;
		}
		else {
			maybe = already + (1LL << bit) * cnt - hack;
			if(maybe <= m) {
				already = maybe;
			}
			else {
				puts("-1");
				return;
			}
		}
	}
	if(already <= m) {
		printf("%lld\n", best);
	}
	else {
		puts("-1");
	}
		
		//~ if(cnt 
	//~ }
	//~ long long total = 0;
	//~ for(ll x : a) {
		//~ total += (x ^ best);
	//~ }
	//~ if(total <= m) {
		//~ printf("%lld\n", best);
	//~ }
	//~ else {
		//~ puts("-1");
	//~ }
}

int main() {
	int T;
	scanf("%d", &T);
	debug() << imie(T);
	for(int nr = 1; nr <= T; ++nr) {
		printf("Case #%d: ", nr);
		test_case();
	}
}


