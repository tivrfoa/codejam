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

const int INF = 1e9 + 5;

int LIMIT;
int answer = INF;
vector<pair<int,int>> s;

void search(vector<int> order, int moves) {
	if(moves > LIMIT) {
		return;
	}
	int n = order.size();
	bool is_sorted = true;
	for(int i = 0; i < n - 1; ++i) {
		if(order[i] > order[i+1]) {
			is_sorted = false;
		}
	}
	if(is_sorted) {
		answer = min(answer, moves);
		debug() << imie(s);
		return;
	}
	for(int a = 1; a < n; ++a) {
		for(int b = 1; a + b <= n; ++b) {
			s.emplace_back(a, b);
			vector<int> nowy;
			for(int i = a; i < a + b; ++i) {
				nowy.push_back(order[i]);
			}
			for(int i = 0; i < a; ++i) {
				nowy.push_back(order[i]);
			}
			for(int i = a + b; i < n; ++i) {
				nowy.push_back(order[i]);
			}
			search(nowy, moves + 1);
			s.pop_back();
		}
	}
}

vector<pair<int,int>> greedy(vector<int> order) {
	int n = order.size();
	vector<pair<int,int>> gather;
	while(true) {
		int want = order[0];
		int i = 0;
		while(order[i] == want) {
			i++;
		}
		while(i < n && order[i] != want) {
			i++;
		}
		if(i == n) {
			break;
		}
		if(i == n - 1) {
			debug() << imie(i);
			--i;
		}
		assert(i != n - 1);
		int want2 = order[i+1];
		int j = i - 1;
		debug() << imie(order) imie(i) imie(j) imie(want) imie(want2);
		while(j >= 0 && order[j] != want2) {
			--j;
		}
		assert(j >= 0);
		gather.emplace_back(j + 1, i - j);
		
		vector<int> nowy;
		for(int k = j + 1; k <= i; ++k) {
			nowy.push_back(order[k]);
		}
		for(int k = 0; k <= j; ++k) {
			nowy.push_back(order[k]);
		}
		for(int k = i + 1; k < (int) order.size(); ++k) {
			nowy.push_back(order[k]);
		}
		assert(nowy.size() == order.size());
		order = nowy;
		debug() << imie(order);
	}
	return gather;
}
		

void test_case() {
	int R, S;
	scanf("%d%d", &R, &S);
	// R cards, then R, then R, ...
	vector<int> order;
	for(int rep = 0; rep < S; ++rep) {
		for(int i = 1; i <= R; ++i) {
			order.push_back(i);
		}
	}
	auto answer = greedy(order);
	
	/*
	for(LIMIT = 0; LIMIT < 10; ++LIMIT) {
		answer = INF;
		search(order, 0);
		if(answer < INF) {
			cerr << imie(LIMIT) imie(answer) << endl;
			return;
		}
	}
	cerr << "NOT FOUND" << endl;
	vector<pair<int,int>> answer;
	for(int value = R; value >= 2; --value) {
		for(int i = (int) order.size() - 1; i >= 0; --i) {
			if(order[i] < value) {
				int j = i;
				while(j >= 0 && order[j] < value) {
					--j;
				}
				if(j == -1) {
					break;
				}
				answer.emplace_back(j + 1, i - j);
				vector<int> nowy;
				for(int k = j + 1; k <= i; ++k) {
					nowy.push_back(order[k]);
				}
				for(int k = 0; k <= j; ++k) {
					nowy.push_back(order[k]);
				}
				for(int k = i + 1; k < (int) order.size(); ++k) {
					nowy.push_back(order[k]);
				}
				assert(nowy.size() == order.size());
				order = nowy;
				assert(order[i] == value);
				debug() << imie(order);
			}
		}
	}*/
	printf("%d\n", (int) answer.size());
	for(pair<int,int> p : answer) {
		printf("%d %d\n", p.first, p.second);
	}
}

int main() {
	int T;
	scanf("%d", &T);
	for(int nr = 1; nr <= T; ++nr) {
		printf("Case #%d: ", nr);
		test_case();
	}
}
