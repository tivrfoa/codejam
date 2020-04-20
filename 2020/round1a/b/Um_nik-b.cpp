#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <algorithm>
#include <cmath>
#include <vector>
#include <set>
#include <map>
#include <unordered_set>
#include <unordered_map>
#include <queue>
#include <ctime>
#include <cassert>
#include <complex>
#include <string>
#include <cstring>
#include <chrono>
#include <random>
#include <bitset>
using namespace std;

#ifdef LOCAL
	#define eprintf(...) fprintf(stderr, __VA_ARGS__);fflush(stderr);
#else
	#define eprintf(...) 42
#endif

using ll = long long;
using ld = long double;
using uint = unsigned int;
using ull = unsigned long long;
template<typename T>
using pair2 = pair<T, T>;
using pii = pair<int, int>;
using pli = pair<ll, int>;
using pll = pair<ll, ll>;
mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());
 
#define pb push_back
#define mp make_pair
#define all(x) (x).begin(),(x).end()
#define fi first
#define se second

double startTime;
double getCurrentTime() {
	return ((double)clock() - startTime) / CLOCKS_PER_SEC;
}

const ll INF = (ll)1e12;
ll add(ll x, ll y) {
	return min(x + y, INF);
}

const int N = 505;
ll C[N][N];
ll S[N][N];

void solve() {
	ll X;
	scanf("%lld", &X);
	int x = 0, y = 0;
	while(true) {
		printf("%d %d\n", x + 1, y + 1);
		X -= C[x][y];
		if (X < 0) throw;
		if (X == 0) return;
		if (X >= S[x + 1][y + 1]) {
			x++;
			y++;
			continue;
		}
		if (X >= S[x + 1][y]) {
			x++;
			continue;
		}
		if (y > 0 && X >= S[x][y - 1]) {
			y--;
			continue;
		}
		throw;
	}
}

int main()
{
	startTime = (double)clock();
//	freopen("input.txt", "r", stdin);
//	freopen("output.txt", "w", stdout);

	for (int i = 0; i < N; i++)
		C[i][0] = C[i][i] = 1;
	for (int i = 1; i < N; i++)
		for (int j = 1; j < i; j++)
			C[i][j] = add(C[i - 1][j - 1], C[i - 1][j]);
	for (int i = 0; i < N; i++) {
		S[i][0] = C[i][0];
		for (int j = 1; j <= i; j++)
			S[i][j] = add(S[i][j - 1], C[i][j]);
	}

	int t;
	scanf("%d", &t);
	for (int i = 1; i <= t; i++) {
		printf("Case #%d:\n", i);
		solve();
	}

	return 0;
}
