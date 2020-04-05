/**
 *    author:  tourist
 *    created: 04.04.2020 22:12:59       
**/
#include <bits/stdc++.h>

using namespace std;

class matching {
 public:
  vector<vector<int>> g;
  vector<int> pa;
  vector<int> pb;
  vector<int> was;
  int n, m;
  int res;
  int iter;

  matching(int _n, int _m) : n(_n), m(_m) {
    assert(0 <= n && 0 <= m);
    pa = vector<int>(n, -1);
    pb = vector<int>(m, -1);
    was = vector<int>(n, 0);
    g.resize(n);
    res = 0;
    iter = 0;
  }

  void add(int from, int to) {
    assert(0 <= from && from < n && 0 <= to && to < m);
    g[from].push_back(to);
  }

  bool dfs(int v) {
    was[v] = iter;
    for (int u : g[v]) {
      if (pb[u] == -1) {
        pa[v] = u;
        pb[u] = v;
        return true;
      }
    }
    for (int u : g[v]) {
      if (was[pb[u]] != iter && dfs(pb[u])) {
        pa[v] = u;
        pb[u] = v;
        return true;
      }
    }
    return false;
  }

  int solve() {
    while (true) {
      iter++;
      int add = 0;
      for (int i = 0; i < n; i++) {
        if (pa[i] == -1 && dfs(i)) {
          add++;
        }
      }
      if (add == 0) {
        break;
      }
      res += add;
    }
    return res;
  }

  int run_one(int v) {
    if (pa[v] != -1) {
      return 0;
    }
    iter++;
    return (int) dfs(v);
  }
};

int main() {
  ios::sync_with_stdio(false);
  cin.tie(0);
  int tt;
  cin >> tt;
  for (int qq = 1; qq <= tt; qq++) {
    cout << "Case #" << qq << ": ";
    int n, k;
    cin >> n >> k;
    k -= n;
    if ((k == 1 || k == (n - 1) * n - 1) || (n == 3 && (k == 2 || k == 4))) {
      cout << "IMPOSSIBLE" << '\n';
      continue;
    }
    cout << "POSSIBLE" << '\n';
    vector<int> diag(n);
    for (int i = 0; i < n; i++) {
      diag[i] = k / n + (i < k % n);
    }
    sort(diag.begin(), diag.end());
    if (diag[0] != diag[1]) {
      diag[1] -= 1;
      diag[n - 1] += 1;
    } else {
      if (diag[n - 2] != diag[n - 1]) {
        diag[n - 2] += 1;
        diag[0] -= 1;
      }
    }
    assert(is_sorted(diag.begin(), diag.end()));
    vector<vector<int>> a(n, vector<int>(n, -1));
    vector<int> freq(n);
    for (int i = 0; i < n; i++) {
      a[i][i] = diag[i];
      freq[diag[i]] += 1;
    }
    for (int f = n; f >= 0; f--) {
      for (int val = 0; val < n; val++) {
        if (freq[val] == f) {
          matching mat(n, n);
          for (int i = 0; i < n; i++) {
            if (a[i][i] == val) {
              mat.add(i, i);
            }
          }
          for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
              if (a[i][j] == -1 && a[i][i] != val && a[j][j] != val) {
                mat.add(i, j);
              }
            }
          }
          int ret = mat.solve();
          assert(ret == n);
          for (int i = 0; i < n; i++) {
            a[i][mat.pa[i]] = val;
          }
        }
      }
    }
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (j > 0) {
          cout << " ";
        }
        cout << a[i][j] + 1;
      }
      cout << '\n';
    }
    {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          assert(0 <= a[i][j] && a[i][j] < n);
        }
      }
      int trace = 0;
      for (int i = 0; i < n; i++) {
        trace += a[i][i];
      }
      assert(trace == k);
      vector<int> was(n);
      int T = 0;
      int rows = 0;
      for (int i = 0; i < n; i++) {
        ++T;
        for (int j = 0; j < n; j++) {
          if (was[a[i][j]] == T) {
            ++rows;
            break;
          }
          was[a[i][j]] = T;
        }
      }
      assert(rows == 0);
      int cols = 0;
      for (int i = 0; i < n; i++) {
        ++T;
        for (int j = 0; j < n; j++) {
          if (was[a[j][i]] == T) {
            ++cols;
            break;
          }
          was[a[j][i]] = T;
        }
      }
      assert(cols == 0);
    }
  }
  return 0;
}
