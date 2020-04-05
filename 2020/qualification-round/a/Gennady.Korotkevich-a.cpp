/**
 *    author:  tourist
 *    created: 04.04.2020 15:19:48       
**/
#include <bits/stdc++.h>

using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(0);
  int tt;
  cin >> tt;
  for (int qq = 1; qq <= tt; qq++) {
    cout << "Case #" << qq << ": ";
    int n;
    cin >> n;
    vector<vector<int>> a(n, vector<int>(n));
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        cin >> a[i][j];
        --a[i][j];
      }
    }
    int trace = 0;
    for (int i = 0; i < n; i++) {
      trace += a[i][i] + 1;
    }
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
    cout << trace << " " << rows << " " << cols << '\n';
  }
  return 0;
}
