#include <bits/stdc++.h>

using namespace std;
typedef long long ll;
const int P = (1 << 17);
const int MAXP = 2 * P + 100;
const int MAXN = 100100;
const ll MOD = 1e9 + 7;

int N;
int arr[MAXP];
int seg[MAXP];
ll fac[MAXN], ifac[MAXN];

int prop (int x, int y) // latest min
{
    if (arr[x] < arr[y]) return x;
    if (arr[x] == arr[y] && x > y) return x;
    return y;
}

ll ans;

int sq (int cloc, int rlo, int rhi, int lo, int hi)
{
    if (rhi < lo || hi < rlo) return P - 1;
    if (lo <= rlo && rhi <= hi) return seg[cloc];

    int rmid = (rlo + rhi) / 2;
    int a = sq (2 * cloc, rlo, rmid, lo, hi);
    int b = sq (2 * cloc + 1, rmid + 1, rhi, lo, hi);
    return prop (a, b);
}

void figure (int lo, int hi, int dep)
{
    int mx = sq (1, 0, P - 1, lo, hi);
    if (arr[mx] != dep)
    {
        ans = 0;
        return;
    }

    int len = (hi - lo), v = (mx - lo);
    ans = (ans * fac[len]) % MOD;
    ans = (ans * ifac[v]) % MOD;
    ans = (ans * ifac[len-v]) % MOD;
    if (mx > lo) figure (lo, mx - 1, dep);
    if (mx < hi) figure (mx + 1, hi, dep + 1);
}

void gogo()
{
    for (int i = 0; i < MAXP; i++)
        arr[i] = 1e9;
    for (int i = P; i < MAXP; i++)
        seg[i] = i - P;

    cin >> N;
    for (int i = 0; i < N; i++)
        cin >> arr[i];
    for (int i = P - 1; i >= 1; i--)
        seg[i] = prop (seg[2*i], seg[2*i+1]);

    ans = 1;

    figure (0, N - 1, 1);
    cout << ans << "\n";
}

ll mpow (ll x, int e)
{
    if (e == 0) return 1;
    if (e == 1) return x % MOD;
    ll v = mpow (x, e / 2);
    v = (v * v) % MOD;
    if (e & 1) v = (v * x) % MOD;
    return v;
}

int main()
{
    int T; cin >> T;

    fac[0] = 1;
    for (int i = 1; i < MAXN; i++)
        fac[i] = (i * fac[i-1]) % MOD;
    ifac[MAXN-1] = mpow (fac[MAXN-1], MOD - 2);
    for (int i = MAXN - 2; i >= 0; i--)
        ifac[i] = ((i+1) * ifac[i+1]) % MOD;

    for (int t = 0; t < T; t++)
    {
        cout << "Case #" << t + 1 << ": ";
        gogo();
    }
}