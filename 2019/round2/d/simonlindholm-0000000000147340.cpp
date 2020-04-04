#include <bits/stdc++.h>
using namespace std;

#define rep(i, from, to) for (int i = from; i < int(to); ++i)
#define trav(a, x) for (auto& a : x)
#define all(x) x.begin(), x.end()
#define sz(x) (int)(x).size()
typedef long long ll;
typedef pair<int, int> pii;
typedef vector<int> vi;

vi val, comp, z, cont;
int Time, ncomps;
template<class G, class F> int dfs(int j, G& g, F f) {
	int low = val[j] = ++Time, x; z.push_back(j);
	trav(e,g[j]) if (comp[e] < 0)
		low = min(low, val[e] ?: dfs(e,g,f));

	if (low == val[j]) {
		do {
			x = z.back(); z.pop_back();
			comp[x] = ncomps;
			cont.push_back(x);
		} while (x != j);
		f(cont); cont.clear();
		ncomps++;
	}
	return val[j] = low;
}
template<class G, class F> void scc(G& g, F f) {
	int n = sz(g);
	val.assign(n, 0); comp.assign(n, -1);
	Time = ncomps = 0;
	rep(i,0,n) if (comp[i] < 0) dfs(i, g, f);
}

const ll mod = 1000000007;
const ll ZERO = -1;
const ll INF = -2;

ll add(ll a, ll b) {
	if (a == ZERO) return b;
	if (b == ZERO) return a;
	if (a == INF) return INF;
	if (b == INF) return INF;
	return (a + b) % mod;
}

void solve() {
	int N;
	cin >> N;
	vector<vi> ed(N);
	vi left(N), right(N);
	rep(i,0,N) {
		cin >> left[i] >> right[i];
		--left[i], --right[i];
		ed[i].push_back(left[i]);
		ed[i].push_back(right[i]);
	}
	vector<ll> val(N);
	rep(i,0,N) cin >> val[i];

	vector<ll> res(N);
	scc(ed, [&](vi& v) {
		int c = comp[v[0]];
		bool innerLead = false;
		trav(x, v) if (x == 0) innerLead = true;
		ll myres;
		if (sz(v) > 1) {
			myres = ZERO;
			trav(x, v) trav(e, ed[x]) if (comp[e] < c) {
				if (res[e] != ZERO) myres = INF;
			}
			if (innerLead) {
				assert(myres == ZERO);
				myres = 1;
				trav(x, v) if (comp[left[x]] == c && comp[right[x]] == c) {
					myres = INF;
				}
			}
		} else {
			if (left[v[0]] == v[0] && right[v[0]] == v[0]) {
				myres = innerLead ? INF : 0;
			} else if (left[v[0]] == v[0] || right[v[0]] == v[0]) {
				myres = ZERO;
				trav(x, v) trav(e, ed[x]) if (comp[e] < c) {
					if (res[e] != ZERO) myres = INF;
				}
				if (innerLead) {
					assert(myres == ZERO);
					myres = 1;
				}
			} else {
				if (innerLead) myres = 1;
				else myres = add(res[left[v[0]]], res[right[v[0]]]);
			}
		}
		trav(x, v) res[x] = myres;
	});

	rep(i,0,N) {
		if (val[i] != 0 && res[i] == INF) {
			cout << "UNBOUNDED" << endl;
			return;
		}
	}

	ll out = 0;
	rep(i,0,N) {
		if (val[i] == 0 || res[i] == ZERO) continue;
		out += val[i] * res[i] % mod;
	}
	out %= mod;
	if (out < 0) out += mod;
	cout << out << endl;
}

int main() {
	cin.sync_with_stdio(false);
	cin.exceptions(cin.failbit | cin.eofbit | cin.badbit);
	cin.tie(0);
	int T;
	cin >> T;
	rep(i,0,T) {
		cout << "Case #" << i+1 << ": ";
		solve();
	}
}
