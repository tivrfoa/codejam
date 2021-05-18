#include <bits/stdc++.h>

using namespace std;
typedef long long ll;
const int MAXN = 1000100;

vector <int> fac[MAXN];

int ans;

void figure (int x, int d)
{
    if (x <= 1) return;

    ans = max (ans, d);

    int lo = 2;
    if (d == 1) lo = 3;
    for (int f : fac[x])
    {
        if (f < lo) continue;
        figure (x / f - 1, d + 1);
    }
}

void gogo()
{
    int N; cin >> N;
    ans = 0;
    figure (N, 1);

    cout << ans << "\n";
}

int main()
{
    int T; cin >> T;

    for (int i = 0; i < MAXN; i++)
        fac[i].reserve(20);

    for (int i = 1; i < MAXN; i++)
        for (int j = i; j < MAXN; j += i)
            fac[j].push_back(i);

    for (int t = 0; t < T; t++)
    {
        cout << "Case #" << t + 1 << ": ";
        gogo();
    }
}