#include <cstdio>
#include <numeric>
#include <iostream>
#include <vector>
#include <set>
#include <cstring>
#include <string>
#include <map>
#include <cmath>
#include <ctime>
#include <algorithm>
#include <bitset>
#include <queue>
#include <sstream>
#include <deque>
#include <cassert>

using namespace std;

#define mp make_pair
#define pb push_back
#define rep(i,n) for(int i = 0; i < (n); i++)
#define re return
#define fi first
#define se second
#define sz(x) ((int) (x).size())
#define all(x) (x).begin(), (x).end()
#define sqr(x) ((x) * (x))
#define sqrt(x) sqrt(abs(x))
#define y0 y3487465
#define y1 y8687969
#define fill(x,y) memset(x,y,sizeof(x))
#define prev PREV
                         
typedef vector<int> vi;
typedef long long ll;
typedef long double ld;
typedef double D;
typedef pair<int, int> ii;
typedef vector<ii> vii;
typedef vector<string> vs;
typedef vector<vi> vvi;

template<class T> T abs(T x) { re x > 0 ? x : -x; }

const int di[4] = {1, 0, -1, 0};
const int dj[4] = {0, 1, 0, -1};

const int N = 100000;

int n;
int m;
int g[N];
int was[N];
int cnt[N];
int sum[N];
int len[N][4];
int q[N];

int main () {
	int tt;
	cin >> tt;
	for (int it = 1; it <= tt; it++) {
		scanf ("%d%d", &n, &m);
		for (int i = 0; i < n * m; i++) scanf ("%d", &g[i]);
		ll cur = 0, ans = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				cur += g[i * m + j];
				was[i * m + j] = 0;
				cnt[i * m + j] = 0;
				sum[i * m + j] = 0;
				for (int t = 0; t < 4; t++) {
					int ni = i + di[t];
					int nj = j + dj[t];
					len[i * m + j][t] = 1;
					if (ni < 0 || nj < 0 || ni >= n || nj >= m) continue;
					cnt[i * m + j]++;
					sum[i * m + j] += g[ni * m + nj];
				}
			}
		int l = 0, r = 0;
		for (int i = 0; i < n * m; i++)
			if (cnt[i] > 0 && sum[i] > g[i] * cnt[i]) {
				q[r++] = i;
				was[i] = 1;
			}
		while (true) {
			ans += cur;
//			printf ("%lld\n", cur);
			if (l == r) break;
			vi w;
			while (l < r) {
				int x = q[l];
				int i = x / m;
				int j = x % m;
				cur -= g[x];
				l++;
//				printf ("%d %d\n", i, j);
				for (int t = 0; t < 4; t++) {
					int ni = i + di[t] * len[x][t];
					int nj = j + dj[t] * len[x][t];
					int ri = i + di[t ^ 2] * len[x][t ^ 2];
					int rj = j + dj[t ^ 2] * len[x][t ^ 2];
					if (ni < 0 || nj < 0 || ni >= n || nj >= m) continue;
//					printf ("%d %d | %d %d -> %d %d\n", len[x][t], len[x][t ^ 2], ni, nj, ri, rj);
					len[ni * m + nj][t ^ 2] += len[x][t ^ 2];
					cnt[ni * m + nj]--;
					sum[ni * m + nj] -= g[x];
					if (!was[ni * m + nj]) w.pb (ni * m + nj);
					if (ri < 0 || rj < 0 || ri >= n || rj >= m) continue;
					cnt[ni * m + nj]++;
					sum[ni * m + nj] += g[ri * m + rj];
				}
			}
			for (auto x : w) {
				if (was[x]) continue;
//				printf ("check %d | %d %d %d\n", x, sum[x], g[x], cnt[x]);
				if (cnt[x] > 0 && sum[x] > g[x] * cnt[x]) {
					was[x] = 1;
					q[r++] = x;
				}
			}
		}
		cout.precision (20);
		cout << "Case #" << it << ": " << ans;
		cout << endl;
		fprintf (stderr, "%d / %d = %.2f | %.2f\n", it, tt, (double)clock () / CLOCKS_PER_SEC, ((double)clock () / it * tt) / CLOCKS_PER_SEC);
	}
	return 0;
}