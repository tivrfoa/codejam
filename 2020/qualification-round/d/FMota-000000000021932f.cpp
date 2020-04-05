#include <bits/stdc++.h>
using namespace std;
template<typename T = int> vector<T> create(size_t n){ return vector<T>(n); }
template<typename T, typename... Args> auto create(size_t n, Args... args){ return vector<decltype(create<T>(args...))>(n, create<T>(args...)); }
struct hopcroft{
    vector<int> dist, pair_l, pair_r;
    vector<bool> seen;
    vector<vector<int>> to;
    int n1, n2;
    hopcroft(int n1, int n2) : n1(n1), n2(n2){
        to.resize(n1);
        pair_l.assign(n1, -1);
        pair_r.assign(n2, -1);
        dist.resize(n1);
        seen.resize(n1);
    }
    void add_edge(int u, int v){
        to[u].push_back(v);
    }
    bool bfs(){
        fill(dist.begin(), dist.end(), -1);
        queue<int> q;
        for (int i = 0; i < n1; i++){
            if (pair_l[i] == -1){
                dist[i] = 0;
                q.push(i);
            }
        }
        bool reach_minus = false;
        while (!q.empty()){
            int u = q.front();
            q.pop();
            for (int adj : to[u]){
                int radj = pair_r[adj];
                if (radj == -1){
                    reach_minus = true;
                } else if (dist[radj] == -1){
                    dist[radj] = dist[u] + 1;
                    q.push(radj);
                }
            }
        }
        return reach_minus;
    }
    bool dfs(int u){
        seen[u] = true;
        for (int adj : to[u]){
            int radj = pair_r[adj];
            if (radj == -1 || (!seen[radj] && dist[radj] == dist[u] + 1 && dfs(radj))){
                pair_l[u] = adj;
                pair_r[adj] = u;
                return true;
            }
        }
        return false;
    }
    int max_matching(){
        int res = 0;
        while (bfs()){
            fill(seen.begin(), seen.end(), false);
            for (int i = 0; i < n1; i++)
                if (pair_l[i] == -1 && dfs(i))
                    res++;
        }
        return res;
    }
};
int main(){
	ios::sync_with_stdio(false);
	cin.tie(0);
	int tt; cin >> tt;
	for(int _ = 1; _ <= tt; _++){
		cout << "Case #" << _ << ":";
		int n, k; cin >> n >> k;
		k -= n;
		auto g = create<int>(n, n);
		for(int i = 0; i < n; i++) for(int j = 0; j < n; j++) g[i][j] = 1;
		auto rg = g;
		bool found = false;
		for(int iter = 0; iter < 200; iter++){
			g = rg;
			for(int j = 0; j < k; j++){
				vector<int> pos;
				for(int z = 0; z < n; z++) if(g[z][z] < n) pos.push_back(z);
				int p = pos[rand() % pos.size()];
				g[p][p]++;
			}
			bool bad = false;
			for(int j = 0; j < n; j++){
				int take = g[j][j] - 1;
				hopcroft hop(n, n);
				for(int i = 0; i < n; i++){
					if(j == i){

					} else {
						vector<int> good(n, 1);
						good[take] = 0;
						good[g[i][i] - 1] = 0;
						for(int k = 0; k < j; k++) good[g[i][k] - 1] = 0;
						for(int k = 0; k < n; k++){
							if(good[k]){
								hop.add_edge(k, i);
							}
						}
					}
				}
				int c = hop.max_matching();
				// cout << "ma " << c << endl;
				if(c == n - 1){
					for(int k = 0; k < n; k++) if(k != take){
						// cout << hop.pair_l[k] << ' ' << j << ' ' << k << endl;
						g[hop.pair_l[k]][j] += k;
					}
				} else {
					bad = true;
					break;
				}
			}
			if(bad){
				continue;
			}
			found = true;
			cout << " POSSIBLE\n";
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					cout << g[i][j] << " \n"[j + 1 == n];
				}
			}
			break;
		}
		if(!found) cout << " IMPOSSIBLE\n";
	}
	return 0;
}
