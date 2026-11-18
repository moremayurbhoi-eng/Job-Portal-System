export const JOB_TYPES = {
  FULL_TIME: 'Full time',
  PART_TIME: 'Part time',
  CONTRACT: 'Contract',
  INTERNSHIP: 'Internship',
  REMOTE: 'Remote',
};

export const STATUSES = ['APPLIED', 'REVIEWING', 'SHORTLISTED', 'REJECTED', 'HIRED'];

const fmt = (n) => (n >= 100000 ? `${(n / 100000).toFixed(n % 100000 ? 1 : 0)}L` : n.toLocaleString());

export function salary(job) {
  if (job.salaryMin == null && job.salaryMax == null) return null;
  if (job.salaryMin != null && job.salaryMax != null) return `${fmt(job.salaryMin)} – ${fmt(job.salaryMax)}`;
  return fmt(job.salaryMin ?? job.salaryMax);
}

export function timeAgo(iso) {
  const days = Math.floor((Date.now() - new Date(iso)) / 86400000);
  if (days < 1) return 'Today';
  if (days === 1) return 'Yesterday';
  if (days < 30) return `${days} days ago`;
  return new Date(iso).toLocaleDateString();
}
