#include <bits/stdc++.h>
using namespace std;

#define Fi first
#define Se second
typedef long long ll;
typedef pair<int,int> pii;
typedef pair<ll,ll> pll;
#define all(x) (x).begin(), (x).end()
#define pb push_back
#define szz(x) (int)(x).size()
#define rep(i, n) for(int i=0;i<(n);i++)
typedef tuple<int, int, int> t3;

namespace Matching{
	//matching [1...n] <-> [1...m]
	const int MX = 120, MY = 120;
	vector <int> E[MX];
	int xy[MX], yx[MY];
	int n, m;

	void addE(int x, int y) { E[x].pb(y); }
	void setnm(int sn, int sm) { n = sn; m = sm; rep(i, MX) E[i].clear(); }

	int tdis[MX], que[MX], *dis = tdis + 1;
	int bfs() {
		int *fr = que, *re = que;
		for(int i=1;i<=n;i++) {
			if(xy[i] == -1) *fr++ = i, dis[i] = 0;
			else dis[i] = -1;
		}
		dis[-1] = -1;
		while(fr != re) {
			int t = *re++;
			if(t == -1) return 1;
			for(int e : E[t]) {
				if(dis[yx[e]] == -1) dis[yx[e]] = dis[t] + 1, *fr++ = yx[e];
			}
		}
		return 0;
	}

	int dfs(int x) {
		for(int e : E[x]) {
			if(yx[e] == -1 || (dis[yx[e]] == dis[x] + 1 && dfs(yx[e]))) {
				xy[x] = e;
				yx[e] = x;
				return 1;
			}
		}
		dis[x] = -1;
		return 0;
	}

	int Do() {
		memset(xy, -1, sizeof xy);
		memset(yx, -1, sizeof yx);

		int ans = 0;
		while(bfs()) {
			for(int i=1;i<=n;i++) if(xy[i] == -1 && dfs(i)) ++ans;
		}
		return ans;
	}
}

int N, K;

void solve() {
	scanf("%d%d", &N, &K);
	if(K == N+1 || K == N*N-1) puts("IMPOSSIBLE");
	else if(N == 3 && (K == 5 || K == 7)) puts("IMPOSSIBLE");
	else {
		int V[55] = {}, ans[55][55] = {};
		for(int i=1;i<=N;i++) V[i] = 1;
		int tk = K - N;
		for(int i=N;i;i--) {
			int v = min(tk, N-1);
			V[i] += v;
			tk -= v;
		}
		if(V[N] > 1 && V[N-1] == 1) {
			V[N-1]++; V[N]--;
		}
		else if(V[1] < N && V[2] == N) {
			V[1]++; V[2]--;
		}
		for(int i=1;i<=N;i++) ans[i][i] = V[i];
		int cnt[110] = {}, ord[110] = {};
		for(int i=1;i<=N;i++) cnt[V[i]]++, ord[i] = i;
		sort(ord+1, ord+1+N, [&](int x, int y) { return cnt[x] > cnt[y]; });
		for(int a=1;a<=N;a++) {
			int i = ord[a];
			Matching::setnm(N, N);
			for(int j=1;j<=N;j++) for(int k=1;k<=N;k++) if(!ans[j][k] && V[j] != i && V[k] != i) Matching::addE(j, k);
			Matching::Do();
			for(int j=1;j<=N;j++) if(Matching::xy[j] != -1) {
				int k = Matching::xy[j];
				ans[j][k] = i;
			}
		}
		puts("POSSIBLE");
		for(int i=1;i<=N;i++, puts("")) for(int j=1;j<=N;j++) printf("%d ", ans[i][j]);
	}
}

int main() {
	int T; scanf("%d", &T);
	for(int t=1;t<=T;t++) {
		printf("Case #%d: ", t);
		solve();
	}
	return 0;
}
