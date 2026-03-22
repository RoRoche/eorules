module.exports = {
  rules: {

    // Type could be empty
    'type-empty': [0],

    // Subject could be empty
    'subject-empty': [0],

    // All types are accepted
    'type-enum': [0],

    // No need to limit the length of the header
    'header-max-length': [2, 'always', 120],

    // Issue mandatory for all commits
    'references-issue': [2, 'always']
  },
  plugins: [
      {
        rules: {
          'references-issue': ({header, body, footer}) => {

            const text = `${header}\n${body || ''}\n${footer || ''}`;

            // #123 ou GH-123
            const regex = /(Fixes|Closes|Resolves|See)\s+#\d+/i;

            const pass = regex.test(text);

            return [
              pass,
              'commit must reference a GitHub issue (example: See #123)'
            ];
          }
        }
      }
    ]
};